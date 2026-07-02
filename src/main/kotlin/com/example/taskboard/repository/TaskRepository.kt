package com.example.taskboard.repository

import com.example.taskboard.models.Tasks
import com.example.taskboard.models.dto.TaskResponse
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TaskRepository {

    fun getTasksByUser(userId: Int): List<TaskResponse> = transaction {
        Tasks.select { Tasks.userId eq userId }
            .map {
                TaskResponse(
                    id = it[Tasks.id],
                    title = it[Tasks.title],
                    status = it[Tasks.status]
                )
            }
    }

    fun addTask(userId: Int, title: String, status: String): TaskResponse = transaction {
        val id = Tasks.insert {
            it[Tasks.title] = title
            it[Tasks.status] = status
            it[Tasks.userId] = userId
        } get Tasks.id

        TaskResponse(id = id, title = title, status = status)
    }

    fun updateTaskStatus(id: Int, userId: Int, status: String): Boolean = transaction {
        val updated = Tasks.update({ (Tasks.id eq id) and (Tasks.userId eq userId) }) {
            it[Tasks.status] = status
        }
        updated > 0
    }

    fun deleteTask(id: Int, userId: Int): Boolean = transaction {
        val deleted = Tasks.deleteWhere { (Tasks.id eq id) and (Tasks.userId eq userId) }
        deleted > 0
    }
}
