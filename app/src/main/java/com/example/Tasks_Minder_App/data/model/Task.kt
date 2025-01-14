package com.example.Tasks_Minder_App.data.model

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val alert: Boolean = false,
    val userId: String = "",
    val notificationId: Int = (0..Int.MAX_VALUE).random() // Add a unique notification ID
)
