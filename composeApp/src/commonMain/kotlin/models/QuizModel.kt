
package models

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class Quiz(
    val name: String,
    var isChecked: Boolean = false
)

class QuizModel {
    private val list = MutableStateFlow<List<Quiz>>(emptyList())
    val quizList: StateFlow<List<Quiz>> = list


    private val deleted = MutableSharedFlow<Quiz>()
    val deletedQuiz: SharedFlow<Quiz> = deleted

    fun addQuiz(quiz: Quiz) {
        list.value = list.value + quiz
    }

    fun removeQuiz(quiz: Quiz) {
        list.value = list.value - quiz

      // notify ui
        deleted.tryEmit(quiz)
    }
}

