package domain.model

enum class CardStateIntent {
    None, Create, Update, Delete
}
data class Card(
    val id: Int,
    var text: String,
    var correctAnswer: String,
    var alternateOption1: String,
    var alternateOption2: String,
    var alternateOption3: String,
    var stateIntent: CardStateIntent = CardStateIntent.None
)
