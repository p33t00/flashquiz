package ui.screens

import domain.LocalDataSource
import domain.model.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class QuizListViewModel(private val dataSource: LocalDataSource): ViewModel() {
    private val _quizzes = MutableStateFlow(listOf<Quiz>())
    val quizzes = _quizzes.asStateFlow()

    fun deleteQuizzes() {
        viewModelScope.launch {
            _quizzes.getAndUpdate { qList -> qList.filter { q -> !q.isChecked } }.let {qList ->
                qList.filter { q -> q.isChecked }.forEach { q -> dataSource.deleteQuiz(q.id) }
            }
        }
    }

    fun checkToggleQuiz(quizId: Int) {
        _quizzes.update { qList -> qList.map {
                q -> if (q.id == quizId) q.copy(isChecked = !q.isChecked) else q; }
        }
    }

    fun initQuizList() {
        _quizzes.value = dataSource.getQuizzes()
    }
}