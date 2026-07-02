package com.example.taskboard.routes

import com.example.taskboard.models.dto.LoginRequest
import com.example.taskboard.models.dto.RegisterRequest
import com.example.taskboard.service.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.authRoutes(userService: UserService) {

    route("/auth") {

        // POST /auth/register -> crea un usuario nuevo (contraseña se guarda hasheada)
        post("/register") {
            val request = call.receive<RegisterRequest>()

            try {
                val user = userService.register(request)
                call.respond(HttpStatusCode.Created, user)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // POST /auth/login -> valida credenciales y devuelve un JWT
        post("/login") {
            val request = call.receive<LoginRequest>()

            try {
                val token = userService.login(request)
                call.respond(HttpStatusCode.OK, token)
            } catch (e: IllegalArgumentException) {
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to e.message))
            }
        }
    }
}
