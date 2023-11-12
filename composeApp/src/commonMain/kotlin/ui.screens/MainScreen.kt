package ui.screens

import TestViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MainScreen(onNavigate: () -> Unit) {
    val viewModel = koinViewModel(vmClass = TestViewModel::class) { parametersOf() }

    val someVal = viewModel.soSaVal.collectAsStateWithLifecycle()
    Column {
        Text("main screen with VM: "  + someVal.value)
        OutlinedButton(onClick = onNavigate) {
            Text("Go Sub")
        }
        OutlinedButton({viewModel.setSomeValue("Yo-ho-ho")}) {
            Text("Change Val")
        }
    }
}