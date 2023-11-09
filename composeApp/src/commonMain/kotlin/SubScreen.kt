import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SubScreen(id: Int?, onNavigate: () -> Unit) {
    Text("Subscreen with ID: " + id)
    OutlinedButton(onClick = onNavigate) {
        Text("Go to Main")
    }
}