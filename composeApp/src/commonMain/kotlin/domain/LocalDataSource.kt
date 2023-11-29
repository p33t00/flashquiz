package domain

import domain.model.Achievement
import domain.model.Card
import domain.model.Quiz


interface LocalDataSource {
    fun getQuizzes(): List<Quiz>
    fun getQuizAchievements(id: Int): List<Achievement>
    fun getQuizCards(id: Int): List<Card>
    fun storeScore(score: Achievement)
    fun insertCard(qid: Int, card: Card)
    fun updateCard(card: Card)
    fun deleteCard(id: Int)
    fun deleteQuiz(id: Int)
    fun insertQuiz(quiz: Quiz)
    fun updateQuiz(quiz: Quiz)
    fun getQuizWithAchievements(id: Int): Quiz?
    fun getQuizWithCards(id: Int): Quiz
}