
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

data class Question(
    var text: String,
    var correctAnswer: String,
    var alternateOption1: String,
    var alternateOption2: String,
    var alternateOption3: String,
)

class QuizModel {
    private val list = MutableStateFlow<List<Quiz>>(emptyList())
    val quizList: StateFlow<List<Quiz>> = list
    private val questions = MutableStateFlow<List<Question>>(emptyList())
    val questionList: StateFlow<List<Question>> = questions

    init {   // for testing
        val dummyQuizzes = listOf(
            Quiz(
                name = "Ml math basics",
                questions = listOf(
                    Question(
                        text = "What is 2 + 2?",
                        correctAnswer = "3",
                         alternateOption1 = "4",
                         alternateOption2 = "5",
                        alternateOption3 = "6"
                        ),
                    Question(
                        text = "What is 2 + 3?",
                        correctAnswer = "3",
                        alternateOption1 = "4",
                        alternateOption2 = "5",
                        alternateOption3 = "6"
                        )
                    ),
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

    fun addQuestion(question: Question) {
        questions.value = questions.value + question
    }
}