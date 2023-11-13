import com.pa1479.bth.g3.flashquiz.database.FlashCardsDB
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

data class Quiz(val id: Int, val name: String)
class TestViewModel(private val db: FlashCardsDB): ViewModel() {
    private val dbQueries = db.flashcardsQueries
    private val _someSavedValue = MutableStateFlow("hello")
    val soSaVal = _someSavedValue.asStateFlow()
    init {
        val quizzes = dbQueries.getQuizzes().executeAsList()

        quizzes.forEach { println(it.name) }

//        savedStateHolder.registerProvider("someValue") {
//            _someSavedValue.value
//        }
    }

    fun setSomeValue(value: String) {

        _someSavedValue.value = value
    }
}