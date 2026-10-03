package com.stayora.app.data.model

data class ChatMessage(
    val id: String,
    val senderId: String, // "student" or "owner"
    val senderName: String,
    val text: String,
    val timestamp: String,
    val isFromStudent: Boolean,
    val isRead: Boolean = true
)

data class ChatConversation(
    val propertyId: String,
    val propertyTitle: String,
    val ownerName: String,
    val ownerRole: String,
    val ownerPhone: String,
    val isVerified: Boolean,
    val messages: List<ChatMessage>,
    val quickQuestions: List<String> = listOf(
        "Is 2-sharing bed vacant from next month?",
        "Can I schedule a visit tomorrow at 5 PM?",
        "Is food/mess included in the monthly rent?",
        "What is the security deposit refund policy?"
    )
)
