package com.example.taskboard.service

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

object JwtService {

    private val secret = System.getenv("JWT_SECRET") ?: "taskboard-secreto-2026-cambia-esto"

    const val issuer = "taskboard-backend"
    const val audience = "taskboard-users"
    private const val validityMs = 24 * 60 * 60 * 1000 // 24 horas

    val algorithm: Algorithm = Algorithm.HMAC256(secret)

    fun generateToken(userId: Int, email: String): String =
        JWT.create()
            .withIssuer(issuer)
            .withAudience(audience)
            .withClaim("userId", userId)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + validityMs))
            .sign(algorithm)
}
