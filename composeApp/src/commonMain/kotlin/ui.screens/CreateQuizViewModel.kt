package ui.screens

import androidx.compose.runtime.mutableStateOf
import domain.LocalDataSource
import domain.model.Card
import domain.model.Quiz
import moe.tlaster.precompose.viewmodel.ViewModel

class CreateQuizViewModel (private val dataSource: LocalDataSource, val quizId: Int = 0): ViewModel() {
    var quiz = mutableStateOf(Quiz(0, ""))
        private set

    var card = mutableStateOf(Card(0, "", "", "", "", ""))
        private set

    init {
        if (quizId > 0) initQuiz(quizId)
    }

    fun initQuiz(id: Int) {
//        TODO: implement getQuizWithCards()
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

    fun updateQuiz() {

    }

    fun updateCard(newCard: Card, oldCard: Card) {
        quiz.value = quiz.value.copy(
            cards = quiz.value.cards.minus(oldCard)
        )
        quiz.value = quiz.value.copy(
            cards = quiz.value.cards.plus(newCard)
        )
        println(quiz.value.cards)
    }
}