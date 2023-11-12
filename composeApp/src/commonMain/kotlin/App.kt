import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
//import dev.icerock.moko.mvvm.compose.getViewModel
//import dev.icerock.moko.mvvm.compose.viewModelFactory
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.compose.rememberKoinInject
import ui.components.AppBar
import ui.screens.LoginScreen
import ui.screens.MainScreen
import ui.screens.QuizListScreen
import ui.screens.SubScreen

enum class RoutesToScreen(val title: String) {
    Home("Home"),
    Login("Login"),
    QuizList("Quiz List"),
    SubScreen("Sub screen")
}
@Composable
fun App() {
    PreComposeApp {
        val navigator = rememberNavigator()
        var currentScreen by remember { mutableStateOf(RoutesToScreen.Home) }
        val scope = rememberCoroutineScope()

        scope.launch {
            navigator.currentEntry.collect {
                currentScreen = RoutesToScreen.valueOf(it?.path?.substringBefore("/") ?: "Home")
                println(currentScreen.name)
            }
        }
        
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

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = androidx.compose.material3.MaterialTheme.colorScheme.background
            ) {
                Scaffold(
                    topBar = {
                        if (currentScreen != RoutesToScreen.Login) {
                            AppBar(currentScreen)
//                            AppBar(
//                                currentScreen,
//                                navController::navigateUp,
//                                {
//                                    scope.launch {
//                                        navDrawerState.open()
//                                    }
//                                },
//                                navController.previousBackStackEntry != null,
//                                { logOutHandler() }
//                            )
                        }
                    }
                ) {
                    NavHost(
                        navigator = navigator,
                        navTransition = NavTransition(),
                        initialRoute = RoutesToScreen.Home.name,
                    ) {
                        scene(
                            route = RoutesToScreen.Home.name,
                            navTransition = NavTransition(),
                        ) {
                            MainScreen({ navigator.navigate(RoutesToScreen.SubScreen.name + "/12") })
                        }
                        scene(
                            route = RoutesToScreen.SubScreen.name + "/{id}",
                            navTransition = NavTransition(),
                        ) { backStackEntry ->
                            SubScreen(
                                backStackEntry.path<Int>("id"),
                                { navigator.navigate(RoutesToScreen.Home.name) }
                            )
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
    }
}