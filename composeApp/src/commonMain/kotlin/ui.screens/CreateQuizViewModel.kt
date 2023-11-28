package ui.screens

import androidx.compose.runtime.mutableStateOf
import domain.LocalDataSource
import domain.model.Card
import domain.model.Quiz
import moe.tlaster.precompose.viewmodel.ViewModel

class CreateQuizViewModel (private val dataSource: LocalDataSource, val quizId: Int = 0): ViewModel() {
    var quiz = mutableStateOf(Quiz(0, ""))
        private set

    init {
        if (quizId > 0) initQuiz(quizId)
    }

    fun initQuiz(id: Int) {
        quiz.value = dataSource.getQuizWithCards(id)
    }

    fun addCard(card: Card) {
        quiz.value = quiz.value.copy(
            cards = quiz.value.cards.plus(card)
        )
        println(quiz.value.cards)
    }

    fun createQuiz(name: String) {
        quiz.value = quiz.value.copy(name = name)
        dataSource.insertQuiz(quiz.value)
    }

    fun getQuiz(id: Int): Quiz {
        quiz.value = dataSource.getQuizWithCards(id)
        return quiz.value
    }
    fun updateQuiz() {
        dataSource.updateQuiz(quiz.value)
    }

    fun updateCard(new: Card, old: Card) {
        quiz.value.cards.find { it == old }?.let { card ->
            card.text = new.text
            card.correctAnswer = new.correctAnswer
            card.alternateOption1 = new.alternateOption1
            card.alternateOption2 = new.alternateOption2
            card.alternateOption3 = new.alternateOption3
        }

        quiz.value = quiz.value.copy(
            cards = quiz.value.cards
        )

        println(quiz.value.cards)
    }
}