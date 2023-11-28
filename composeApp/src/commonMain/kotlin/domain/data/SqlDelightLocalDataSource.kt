package domain.data

import domain.model.Quiz
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import domain.LocalDataSource
import domain.model.Achievement
import domain.model.Card

class SqlDelightLocalDataSource(db: FlashCardsDB): LocalDataSource {
    private val queries = db.flashcardsQueries
    override fun getQuizzes(): List<Quiz> {
        return queries.getQuizzes().executeAsList().map{ Quiz(it.id.toInt(), it.name, listOf()) }
    }

    override fun getQuizWithAchievements(id: Int): Quiz? {
        val qWithAchievements = queries.getQuizWithAchievements(id.toLong()).executeAsList()
        return if (qWithAchievements.isEmpty()) { null }
        else {
            qWithAchievements.let { qWa ->
                val row = qWa.first()
                val achievements: List<Achievement> = qWa
                    .filter { a -> a.score != null && a.created != null }
                    .map { Achievement(it.quiz_id.toInt(), it.score!!, it.created!!) }
                    Quiz(id = id, name = row.quiz_name, achievements = achievements)
            }
        }
    }

    override fun getQuizAchievements(id: Int): List<Achievement> {
        return queries.getQuizAchievements(id.toLong()).executeAsList()
            .map { Achievement(it.quiz_id.toInt(), it.score, it.created) }
    }

    override fun getQuizCards(id: Int): List<Card> {
        return queries.getQuizCards(id.toLong()).executeAsList().map {
            Card(
                it.id.toInt(),
                it.text,
                it.correct_answer,
                it.option_1,
                it.option_2,
                it.option_3
            )
        }
    }

    override fun storeScore(score: Achievement) {
        queries.storeScore(score.quizId.toLong(), score.score, score.created)
    }

    override fun deleteCard(id: Int) {
        queries.deleteCard(id.toLong())
    }

    override fun deleteQuiz(id: Int) {
        queries.deleteQuiz(id.toLong())
    }

    override fun getQuizWithCards(id: Int): Quiz {
        val qNaRaw = queries.getQuizWithCards(id.toLong()).executeAsList()

        if (qNaRaw.isEmpty()) throw NoSuchElementException("Record with provided id: $id does not exist")

        val record = qNaRaw.first()

        val cards: List<Card> = record.id?.let {
            qNaRaw.map {
                Card(
                    it.id!!.toInt(),
                    it.text ?: "",
                    it.correct_answer ?: "",
                    it.option_1 ?: "",
                    it.option_2 ?: "",
                    it.option_3 ?: ""
                )
            }
        } ?: listOf()

        return Quiz(record.quiz_id.toInt(), record.quiz_name, cards)
    }

    override fun insertQuiz(quiz: Quiz) {
        val qid: Long = queries.insertQuiz(quiz.name).executeAsOne()
        quiz.cards.forEach {
            queries.insertCard(
                qid,
                it.text,
                it.correctAnswer,
                it.alternateOption1,
                it.alternateOption2,
                it.alternateOption3
            )
        }
    }

    override fun updateQuiz(quiz: Quiz) {
        queries.updateQuiz(id = quiz.id.toLong(), name = quiz.name)
        quiz.cards.forEach {
            if (it.id > 0) {
                queries.updateCard(
                    id = it.id.toLong(),
                    text = it.text,
                    correct = it.correctAnswer,
                    opt1 = it.alternateOption1,
                    opt2 = it.alternateOption2,
                    opt3 = it.alternateOption3
                )
            } else {
                queries.insertCard(
                    quiz.id.toLong(),
                    it.text,
                    it.correctAnswer,
                    it.alternateOption1,
                    it.alternateOption2,
                    it.alternateOption3
                )
            }
        }
    }
}