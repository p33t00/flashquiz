import domain.LocalDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class TestViewModel(private val dataSource: LocalDataSource): ViewModel() {
    private val _someSavedValue = MutableStateFlow("hello")
    val soSaVal = _someSavedValue.asStateFlow()
    init {
        val quizzes = dataSource.getQuizzes().executeAsList()

        quizzes.forEach { println(it.name) }

//        savedStateHolder.registerProvider("someValue") {
//            _someSavedValue.value
//        }
    }

    fun setSomeValue(value: String) {

        _someSavedValue.value = value
    }
}