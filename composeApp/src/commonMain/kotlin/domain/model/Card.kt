package domain.model

data class Card(
    val id: Int,
    val question: String,
    val answerA: String,
    val answerB: String,
    val answerC: String,
    val answerD: String,
    val correct: String
)
