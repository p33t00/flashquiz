package models

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

data class Quiz(
    val name: String,
    var isChecked: Boolean = false,
    val questions: List<Question>,
    val stats: List<QuizStat> = emptyList()
)

data class Question(
    var text: String,
    var correctAnswer: String,
    var alternateOption1: String,
    var alternateOption2: String,
    var alternateOption3: String
)
data class QuizStat(
    val score: Int,
    val totalQuestions: Int,
    val date: String
)

class QuizModel {
    private val list = MutableStateFlow<List<Quiz>>(emptyList())
    val quizList: StateFlow<List<Quiz>> = list

    init {
        // for testing
        val dummyQuizzes = listOf(
            Quiz(
                name = "Ml math basics",
                questions = listOf(
                    Question(
                        text = "Find the Missing Term in Multiples of 6: \n 6, 12, 18, 24, _, 36, 42, _ 54, 60: \n",
                        correctAnswer = "32, 45",
                        alternateOption1 = "30, 48",
                        alternateOption2 = "24, 40",
                        alternateOption3 = "25, 49"
                    ),
                    Question(
                        text = "What is 2 + 3?",
                        correctAnswer = "5",
                        alternateOption1 = "3",
                        alternateOption2 = "4",
                        alternateOption3 = "1"
                    ),
                )
                )
            )


        list.value = dummyQuizzes
    }

    private val deleted = MutableSharedFlow<Quiz>()
    val deletedQuiz: SharedFlow<Quiz> = deleted

    fun removeQuiz(quiz: Quiz) {
        list.value = list.value - quiz

        // notify ui
        deleted.tryEmit(quiz)
    }

    fun addQuizStat(
        quiz: Quiz,
        score: Int,
        totalQuestions: Int,
        date: String) {

        val updatedQuiz = quiz.copy(
            stats = quiz.stats + QuizStat(score, totalQuestions, date))
        list.value = list.value.map { if (it == quiz) updatedQuiz else it }
    }

}

