package com.example.taskboard

import com.example.taskboard.plugins.configureDatabase
import com.example.taskboard.plugins.configureRouting
import com.example.taskboard.plugins.configureSecurity
import com.example.taskboard.plugins.configureSerialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080

    embeddedServer(Netty, port = port, host = "0.0.0.0") {
        configureDatabase()
        configureSerialization()
        configureSecurity()
        configureRouting()
    }.start(wait = true)
}
