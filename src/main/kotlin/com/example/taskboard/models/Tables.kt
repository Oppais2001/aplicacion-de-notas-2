package com.example.taskboard.models

import org.jetbrains.exposed.sql.Table

// Tabla de usuarios (para login y JWT)
object Users : Table("users") {
    val id = integer("id").autoIncrement()
    val email = varchar("email", 150).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val name = varchar("name", 100)

    override val primaryKey = PrimaryKey(id)
}

// Tabla de tareas (misma info que el modelo Task del frontend)
object Tasks : Table("tasks") {
    val id = integer("id").autoIncrement()
    val title = varchar("title", 200)
    val status = varchar("status", 20) // TODO, IN_PROGRESS, DONE
    val userId = integer("user_id").references(Users.id)

    override val primaryKey = PrimaryKey(id)
}
