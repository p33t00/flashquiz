package ui.screens

import domain.LocalDataSource
import domain.model.Card
import moe.tlaster.precompose.viewmodel.ViewModel

class QuizViewModel(private val dataSource: LocalDataSource): ViewModel() {
    fun getQuizCards(id: Int?): List<Card> {
        return if (id == null) listOf() else dataSource.getQuizCards(id)
    }
}