package com.example.smart_food_planner.model.dataClasses


// Request
data class GenerateContentRequest(
    val contents: List<Content>
)

data class Content(
    val parts: List<Part>
)

data class Part(
    val text: String
)

// Response
data class GenerateContentResponse(
    val candidates: List<Candidate>?
)

data class Candidate(
    val content: Content
)

data class Chat_Bot_Messages(
    val userMessages : MutableList<String>  ,
    val botMessages : MutableList<String> ,
)

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)


