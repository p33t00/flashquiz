import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
//import dev.icerock.moko.mvvm.compose.getViewModel
//import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.screens.LoginScreen
import ui.screens.MainScreen
import ui.screens.QuizListScreen
import ui.screens.SubScreen

enum class RoutesToScreen(val title: String) {
    Home("/home"),
    Login("/login"),
    QuizList("/quizlist")
}
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

//            Scaffold {
//
//            }
            NavHost(
                navigator = navigator,
                navTransition = NavTransition(),
                initialRoute = RoutesToScreen.Login.name,
            ) {
                scene(
                    route = RoutesToScreen.Home.name,
                    navTransition = NavTransition(),
                ) {
                    MainScreen({navigator.navigate("/sub/12")})
                }
                scene(
                    route = RoutesToScreen.Home.name + "/{id}",
                    navTransition = NavTransition(),
                ) {backStackEntry ->
                    SubScreen(backStackEntry.path<Int>("id"), {navigator.navigate("/home")})
                }
                scene(
                    route = RoutesToScreen.Login.name,
                    navTransition = NavTransition(),
                ) {
                    LoginScreen()
                }
                scene(
                    route = RoutesToScreen.QuizList.name,
                    navTransition = NavTransition(),
                ) {
                    QuizListScreen()
                }
                // TODO: Please add more screens and don't forget to add RoutesToScreen route path
            }

        }
    }
}