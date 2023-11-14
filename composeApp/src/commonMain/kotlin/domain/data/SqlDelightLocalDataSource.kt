package domain.data

import app.cash.sqldelight.Query
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import database.Quiz
import domain.LocalDataSource

class SqlDelightLocalDataSource(db: FlashCardsDB): LocalDataSource {
    private val queries = db.flashcardsQueries

    override fun getQuizzes(): Query<Quiz> {
        return queries.getQuizzes()
    }

    override suspend fun insertQuiz(quiz: Quiz) {
        TODO("Not yet implemented")
    }
}