package com.example.taskboard.repository

import com.example.taskboard.models.Users
import com.example.taskboard.models.dto.UserResponse
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class UserRow(
    val id: Int,
    val name: String,
    val email: String,
    val passwordHash: String
)

class UserRepository {

    fun findByEmail(email: String): UserRow? = transaction {
        Users.select { Users.email eq email }
            .map {
                UserRow(
                    id = it[Users.id],
                    name = it[Users.name],
                    email = it[Users.email],
                    passwordHash = it[Users.passwordHash]
                )
            }
            .singleOrNull()
    }

    fun createUser(name: String, email: String, passwordHash: String): UserResponse = transaction {
        val id = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
        } get Users.id

        UserResponse(id = id, name = name, email = email)
    }
}
