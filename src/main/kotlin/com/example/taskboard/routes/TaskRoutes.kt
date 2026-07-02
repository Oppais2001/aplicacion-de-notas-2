package com.example.taskboard.routes

import com.example.taskboard.models.dto.TaskRequest
import com.example.taskboard.service.TaskService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.taskRoutes(taskService: TaskService) {

    // Todo lo de /tasks requiere JWT válido en el header Authorization: Bearer <token>
    authenticate("auth-jwt") {

        route("/tasks") {

            // GET /tasks -> lista las tareas del usuario logueado
            get {
                val userId = call.userId()
                call.respond(taskService.getTasks(userId))
            }

            // POST /tasks -> crea una tarea nueva
            post {
                val userId = call.userId()
                val request = call.receive<TaskRequest>()
                call.respond(HttpStatusCode.Created, taskService.addTask(userId, request))
            }

            // PUT /tasks/{id} -> actualiza el estado de una tarea (TODO, IN_PROGRESS, DONE)
            put("/{id}") {
                val userId = call.userId()
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@put call.respond(HttpStatusCode.BadRequest, "Id inválido")

                val request = call.receive<TaskRequest>()
                val updated = taskService.updateTaskStatus(userId, id, request.status)

                if (updated) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }

            // DELETE /tasks/{id} -> elimina una tarea
            delete("/{id}") {
                val userId = call.userId()
                val id = call.parameters["id"]?.toIntOrNull()
                    ?: return@delete call.respond(HttpStatusCode.BadRequest, "Id inválido")

                val deleted = taskService.deleteTask(userId, id)

                if (deleted) call.respond(HttpStatusCode.OK)
                else call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}

private fun ApplicationCall.userId(): Int {
    val principal = principal<JWTPrincipal>()!!
    return principal.payload.getClaim("userId").asInt()
}
