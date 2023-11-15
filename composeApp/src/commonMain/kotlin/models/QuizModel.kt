
package models

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class Quiz(
    val name: String,
    var isChecked: Boolean = false,
    val questions: List<Question>
)

data class Answer(
    val text: String,
    val isCorrect: Boolean
)

data class Question(
    val text: String,
    val answers: List<Answer>
)

class QuizModel {
    private val list = MutableStateFlow<List<Quiz>>(emptyList())
    val quizList: StateFlow<List<Quiz>> = list

    init {   // for testing
        val dummyQuizzes = listOf(
            Quiz(
                name = "Ml math basics",
                questions = listOf(
                    Question(
                        text = "What is 2 + 2?",
                        answers = listOf(
                            Answer(text = "3", isCorrect = false),
                            Answer(text = "4", isCorrect = true),
                            Answer(text = "5", isCorrect = false)
                        )
                    ),
                    Question(
                        text = "What is 2 + 3?",
                        answers = listOf(
                            Answer(text = "3", isCorrect = false),
                            Answer(text = "4", isCorrect = false),
                            Answer(text = "5", isCorrect = true)
                        )
                    )
                )
            )
        )

        list.value = dummyQuizzes
    }

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