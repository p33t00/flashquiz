package domain

import app.cash.sqldelight.Query
import database.Quiz


interface LocalDataSource {
    fun getQuizzes(): Query<Quiz>
    suspend fun insertQuiz(quiz: Quiz)
}