package com.example.taskboard.plugins

import com.auth0.jwt.JWT
import com.example.taskboard.service.JwtService
import io.ktor.server.application.Application
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity() {
    authentication {
        jwt("auth-jwt") {
            verifier(
                JWT.require(JwtService.algorithm)
                    .withIssuer(JwtService.issuer)
                    .withAudience(JwtService.audience)
                    .build()
            )

            validate { credential ->
                if (credential.payload.getClaim("userId").asInt() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
}
