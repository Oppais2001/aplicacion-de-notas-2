package com.example.taskboard.service

import com.example.taskboard.models.dto.LoginRequest
import com.example.taskboard.models.dto.RegisterRequest
import com.example.taskboard.models.dto.TokenResponse
import com.example.taskboard.models.dto.UserResponse
import com.example.taskboard.repository.UserRepository

class UserService(
    private val userRepository: UserRepository
) {

    fun register(request: RegisterRequest): UserResponse {

        val existing = userRepository.findByEmail(request.email)

        if (existing != null) {
            throw IllegalArgumentException("Ya existe un usuario con ese correo")
        }

        val hash = PasswordService.hash(request.password)

        return userRepository.createUser(
            name = request.name,
            email = request.email,
            passwordHash = hash
        )
    }

    fun login(request: LoginRequest): TokenResponse {

        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("Correo o contraseña incorrectos")

        val validPassword = PasswordService.verify(request.password, user.passwordHash)

        if (!validPassword) {
            throw IllegalArgumentException("Correo o contraseña incorrectos")
        }

        val token = JwtService.generateToken(user.id, user.email)

        return TokenResponse(token = token)
    }
}
