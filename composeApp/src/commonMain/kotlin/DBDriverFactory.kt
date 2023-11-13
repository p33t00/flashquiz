import app.cash.sqldelight.db.SqlDriver

expect class DBDriverFactory {
    fun createDriver(): SqlDriver
}