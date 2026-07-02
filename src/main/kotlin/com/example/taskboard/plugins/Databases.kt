package com.example.taskboard.plugins

import com.example.taskboard.models.Tasks
import com.example.taskboard.models.Users
import io.ktor.server.application.Application
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabase() {

    // Estos 3 valores salen de Neon: Dashboard del proyecto -> Connection Details
    // (dejados como valor por defecto para no tener que configurar variables de entorno)
    val url = System.getenv("DATABASE_URL")
        ?: "jdbc:postgresql://ep-odd-recipe-acon595i-pooler.sa-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require"

    val user = System.getenv("DATABASE_USER") ?: "neondb_owner"
    val password = System.getenv("DATABASE_PASSWORD") ?: "npg_fr1aRmvi6gZD"

    Database.connect(
        url = url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )

    // Crea las tablas automáticamente si no existen (útil para desarrollo)
    transaction {
        SchemaUtils.createMissingTablesAndColumns(Users, Tasks)
    }
}
