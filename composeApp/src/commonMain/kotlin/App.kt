import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import domain.model.Achievement
import domain.model.Card
import kotlinx.coroutines.launch
import models.QuizModel
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.KoinContext
import org.koin.core.parameter.parametersOf
import ui.components.AppBar
import ui.screens.CreateQuizScreen
import ui.screens.LoginScreen
import ui.screens.MainScreen
import ui.screens.QuizListScreen
import ui.screens.QuizListViewModel
import ui.screens.QuizScreen
import ui.screens.QuizViewModel
import ui.screens.SubScreen

enum class RoutesToScreen(val title: String) {
    Home("Home"),
    Login("Login"),
    Quiz("Quiz"),
    QuizStats("Quiz Statistics"),
    QuizList("Quiz List"),
    SubScreen("Sub screen"),
    CreateQuiz("Create Quiz")
}
@Composable
fun App() {
    PreComposeApp {
        KoinContext {
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
//                        topBar = {
//                            if (currentScreen != RoutesToScreen.Login) {
//                                AppBar(currentScreen)
////                            AppBar(
////                                currentScreen,
////                                navController::navigateUp,
////                                {
////                                    scope.launch {
////                                        navDrawerState.open()
////                                    }
////                                },
////                                navController.previousBackStackEntry != null,
////                                { logOutHandler() }
////                            )
//                            }
//                        }
                    ) {
                        NavHost(
                            navigator = navigator,
                            navTransition = NavTransition(),
                            initialRoute = RoutesToScreen.QuizList.name,
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
                                route = RoutesToScreen.Quiz.name + "/{id}",
                                navTransition = NavTransition(),
                            ) {backStackEntry ->
                                val quizId = backStackEntry.path<Int>("id")
                                val quizViewModel = koinViewModel(vmClass = QuizViewModel::class) { parametersOf() }
                                val cards: List<Card> = quizViewModel.getQuizCards(quizId)

                                QuizScreen(
                                    cards = cards,
                                    onBackClick = { navigator.popBackStack() },
                                    backToQuizListClick = { navigator.navigate(RoutesToScreen.QuizList.name) },
                                    onQuizComplete = {score: String -> quizViewModel.storeScore(Achievement(quizId!!, score, ""))}
                                )
                            }
                            scene(
                                route = RoutesToScreen.QuizList.name,
                                navTransition = NavTransition(),
                            ) {
                                val quizListViewModel = koinViewModel(vmClass = QuizListViewModel::class) { parametersOf() }
                                val quizzes by quizListViewModel.quizzes.collectAsState()

                                QuizListScreen(
                                    quizzes = quizzes,
                                    currentScreen = currentScreen,
                                    onAddQuizClick = { navigator.navigate(RoutesToScreen.CreateQuiz.name) },
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) },
                                    onQuizClick = { selectedQuiz ->
                                        navigator.navigate(RoutesToScreen.Quiz.name + "/1")
                                    },
                                    onQuizDelete = { quizListViewModel.deleteQuizzes() },
                                    onQuizChecked = {id -> quizListViewModel.checkToggleQuiz(id) }
                                )
                            }

                            scene(
                                route = RoutesToScreen.CreateQuiz.name,
                                navTransition = NavTransition(),
                            ) {
                                val quizModel = QuizModel()

                                CreateQuizScreen(
                                    quizModel = quizModel,
                                    onBackClick = { navigator.popBackStack() },
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) },
                                    onSaveClick = { navigator.popBackStack() }
                                )
                            }
                            // TODO: Please add more screens and don't forget to add RoutesToScreen route path
                        }
                    }
                }
            }
        }
    }
}