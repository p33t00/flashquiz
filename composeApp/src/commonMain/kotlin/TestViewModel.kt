import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class TestViewModel(private val hello: String): ViewModel() {
    private val _someSavedValue = MutableStateFlow(hello)
    val soSaVal = _someSavedValue.asStateFlow()
//    init {
//        savedStateHolder.registerProvider("someValue") {
//            _someSavedValue.value
//        }
//    }

    fun setSomeValue(value: String) {
        _someSavedValue.value = value
    }
}