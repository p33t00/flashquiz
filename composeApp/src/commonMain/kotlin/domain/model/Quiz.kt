package domain.model

data class Quiz(
    val id: Int,
    val name: String,
    val cards: List<Card> = listOf(),
    var isChecked: Boolean = false,
    val achievements: List<Achievement> = emptyList()
)