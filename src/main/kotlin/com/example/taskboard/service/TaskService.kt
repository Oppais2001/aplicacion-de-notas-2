package com.example.taskboard.service

import com.example.taskboard.models.dto.TaskRequest
import com.example.taskboard.models.dto.TaskResponse
import com.example.taskboard.repository.TaskRepository

class TaskService(
    private val taskRepository: TaskRepository
) {

    fun getTasks(userId: Int): List<TaskResponse> =
        taskRepository.getTasksByUser(userId)

    fun addTask(userId: Int, request: TaskRequest): TaskResponse =
        taskRepository.addTask(userId, request.title, request.status)

    fun updateTaskStatus(userId: Int, taskId: Int, status: String): Boolean =
        taskRepository.updateTaskStatus(taskId, userId, status)

    fun deleteTask(userId: Int, taskId: Int): Boolean =
        taskRepository.deleteTask(taskId, userId)
}
