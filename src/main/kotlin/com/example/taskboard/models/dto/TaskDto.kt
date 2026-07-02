package com.example.taskboard.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class TaskRequest(
    val title: String = "",
    val status: String = "TODO"
)

@Serializable
data class TaskResponse(
    val id: Int,
    val title: String,
    val status: String
)
