package com.example.taskboard.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class TokenResponse(
    val token: String
)

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val email: String
)
