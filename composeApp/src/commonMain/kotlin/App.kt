
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import domain.model.Card
import kotlinx.coroutines.launch
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import org.koin.compose.KoinContext
import org.koin.core.parameter.parametersOf
import ui.screens.LoginScreen
import ui.screens.LoginViewModel
import ui.screens.MainScreen
import ui.screens.QuizListScreen
import ui.screens.QuizListViewModel
import ui.screens.QuizScreen
import ui.screens.QuizStatsScreen
import ui.screens.QuizStatsViewModel
import ui.screens.QuizViewModel
import ui.screens.SignUpScreen
import ui.screens.SignupViewModel
import ui.screens.SubScreen

enum class RoutesToScreen(val title: String) {
    Home("Home"),
    Login("Login"),
    SignUp("SignUp"),
    Quiz("Quiz"),
    QuizStats("Quiz Statistics"),
    QuizList("Quiz List"),
    SubScreen("Sub screen")
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
                            // For LoginScreen
                            scene(
                                route = RoutesToScreen.Login.name,
                                navTransition = NavTransition(),
                            ) { backStackEntry ->
                                val loginViewModel = koinViewModel(vmClass = LoginViewModel::class) { parametersOf() }
                                val loginState by loginViewModel.loginState.collectAsState()

                                LoginScreen(
                                    onLoginButtonClick = {
                                        navigator.navigate(RoutesToScreen.QuizList.name)
                                    },
                                    onNavigateToSignUp = {
                                        navigator.navigate(RoutesToScreen.SignUp.name)
                                    },
                                    loginModel = loginViewModel
                                )
                            }

                            // For SignUpScreen
                            scene(
                                route = RoutesToScreen.SignUp.name,
                                navTransition = NavTransition(),
                            ) { backStackEntry ->
                                val signupViewModel = koinViewModel(vmClass = SignupViewModel::class) { parametersOf() }
                                val signupState by signupViewModel.signupState.collectAsState()

                                SignUpScreen(
                                    onSignupButtonClick = {
                                        navigator.navigate(RoutesToScreen.QuizList.name)
                                    },
                                    onNavigateToLogin = {
                                        navigator.navigate(RoutesToScreen.Login.name)
                                    },
                                    signupModel = signupViewModel
                                )
                            }

                            scene(
                                route = RoutesToScreen.Quiz.name + "/{id}",
                                navTransition = NavTransition(),
                            ) {backStackEntry ->
                                val quizViewModel = koinViewModel(vmClass = QuizViewModel::class) { parametersOf() }
                                val cards: List<Card> = quizViewModel.getQuizCards(backStackEntry.path<Int>("id"))
                                QuizScreen(
                                    cards = cards,
                                    onBackClick = { navigator.popBackStack() },
                                    backToQuizListClick = { navigator.navigate(RoutesToScreen.QuizList.name) }
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
                                    onAddQuizClick = { /* navigate to add quiz screen */ },
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) },
                                    onQuizClick = { selectedQuiz ->
                                        navigator.navigate(RoutesToScreen.QuizStats.name + "/${selectedQuiz.id}")
                                    },
                                    onQuizDelete = { quizListViewModel.deleteQuizzes() },
                                    onQuizChecked = {id -> quizListViewModel.checkToggleQuiz(id) }
                                )
                            }
                            scene(
                                route = RoutesToScreen.QuizStats.name + "/{quizId}",
                                navTransition = NavTransition(),
                            ) { backStackEntry ->
                                val quizId = backStackEntry.path<Int?>("quizId") ?: -1
                                val quizStatsViewModel = koinViewModel(vmClass = QuizStatsViewModel::class) {
                                    parametersOf(quizId)
                                }
                                val quiz = quizStatsViewModel.quiz.value

                                if (quiz != null) {
                                    QuizStatsScreen(
                                        quiz = quiz,
                                        onBackClick = {
                                            navigator.popBackStack()
                                        },
                                        onEditQuizClick = {
                                        },
                                        onDeleteQuizClick = {
                                            //to be added
                                        },
                                        onLogoutClick = {
                                            navigator.navigate(RoutesToScreen.Login.name)
                                        },
                                        onQuizClick = {
                                            navigator.navigate(RoutesToScreen.Quiz.name + "/$quizId")
                                        }
                                    )
                                } else {
                                    Text("There has been some error with quiz. Please email us regarding this matter techsup@flashq.com")
                                }
                            }
                            /*  scene(
                                  route = RoutesToScreen.QuizView.name + "/{quizId}",
                                  navTransition = NavTransition(),
                              ) { backStackEntry ->
                                  val quizId = backStackEntry.path<Int>("quizId")
                                  val quiz = quizzes.find { it.id == quizId }
                                      QuizViewScreen(
                                          quiz = quiz,
                                          onBackClick = { navigator.popBackStack() },
                                          backToQuizListClick = { navigator.navigate(RoutesToScreen.QuizList.name)
                                          }
                                      )
                              }  */
                            // TODO: Please add more screens and don't forget to add RoutesToScreen route path
                        }
                    }
                }
            }
        }
    }
}

