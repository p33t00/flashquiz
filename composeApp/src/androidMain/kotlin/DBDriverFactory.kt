import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB

actual class DBDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(FlashCardsDB.Schema, context, "flashcards.db")
    }
}