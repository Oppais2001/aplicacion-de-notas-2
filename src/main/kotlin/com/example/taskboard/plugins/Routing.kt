package com.example.taskboard.plugins

import com.example.taskboard.repository.TaskRepository
import com.example.taskboard.repository.UserRepository
import com.example.taskboard.routes.authRoutes
import com.example.taskboard.routes.taskRoutes
import com.example.taskboard.service.TaskService
import com.example.taskboard.service.UserService
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    // Aquí se arma la cadena: Repository -> Service -> Route
    val userRepository = UserRepository()
    val taskRepository = TaskRepository()

    val userService = UserService(userRepository)
    val taskService = TaskService(taskRepository)

    routing {
        get("/") {
            call.respondText("TaskBoard backend funcionando 🚀")
        }

        authRoutes(userService)
        taskRoutes(taskService)
    }
}
