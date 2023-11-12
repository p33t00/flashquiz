import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.stateholder.SavedStateHolder
import moe.tlaster.precompose.viewmodel.ViewModel

class TestViewModel(savedStateHolder: SavedStateHolder): ViewModel() {
    private val _someSavedValue = MutableStateFlow(savedStateHolder.consumeRestored("someValue") as String? ?: "DefaultHello")
    val soSaVal = _someSavedValue.asStateFlow()
    init {
        savedStateHolder.registerProvider("someValue") {
            _someSavedValue.value
        }
    }

    fun setSomeValue(value: String) {
        _someSavedValue.value = value
    }
}