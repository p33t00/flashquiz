import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
//import dev.icerock.moko.mvvm.compose.getViewModel
//import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    PreComposeApp {
        MaterialTheme {
//            var greetingText by remember { mutableStateOf("Hello World!") }
//            var showImage by remember { mutableStateOf(false) }
//            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                Button(onClick = {
//                    greetingText = "Compose: ${Greeting().greet()}"
//                    showImage = !showImage
//                }) {
//                    Text(greetingText)
//                }
//                AnimatedVisibility(showImage) {
//                    Image(
//                        painterResource("compose-multiplatform.xml"),
//                        null
//                    )
//                }
//            }

            val navigator = rememberNavigator()
            NavHost(
                // Assign the navigator to the NavHost
                navigator = navigator,
                // Navigation transition for the scenes in this NavHost, this is optional
                navTransition = NavTransition(),
                // The start destination
                initialRoute = "/home",
            ) {
                // Define a scene to the navigation graph
                scene(
                    // Scene's route path
                    route = "/home",
                    // Navigation transition for this scene, this is optional
                    navTransition = NavTransition(),
                ) {
                    MainScreen({navigator.navigate("/sub/12")})
                }
                scene(
                    // Scene's route path
                    route = "/sub/{id}",
                    // Navigation transition for this scene, this is optional
                    navTransition = NavTransition(),
                ) {backStackEntry ->
                    SubScreen(backStackEntry.path<Int>("id"), {navigator.navigate("/home")})
                }
            }

        }
    }
}