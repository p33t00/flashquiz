package ui.screens

import domain.LocalDataSource
import domain.lib.FcDateTime
import domain.model.Achievement
import domain.model.Card
import moe.tlaster.precompose.viewmodel.ViewModel

class QuizViewModel(private val dataSource: LocalDataSource): ViewModel() {

    init {
        println(dataSource.getQuizAchievements(1))
    }
    fun getQuizCards(id: Int?): List<Card> {
        return if (id == null) listOf() else dataSource.getQuizCards(id)
    }

    fun storeScore(score: Achievement) {
        score.created = FcDateTime().nowMedium()
        dataSource.storeScore(score)
    }
}