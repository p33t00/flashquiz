package ui.screens

import androidx.compose.runtime.mutableStateOf
import domain.LocalDataSource
import domain.model.Quiz
import moe.tlaster.precompose.viewmodel.ViewModel

class QuizStatsViewModel(val quizId: Int, private val dataSource: LocalDataSource): ViewModel() {
    val quiz = mutableStateOf<Quiz?>(null)

    init {
        quiz.value = dataSource.getQuizWithAchievements(quizId)
    }

    fun deleteQuiz(id: Int) = dataSource.deleteQuiz(id)
}