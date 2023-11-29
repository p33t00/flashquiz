
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
import domain.model.Achievement
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
import ui.screens.CreateQuizScreen
import ui.screens.CreateQuizViewModel
import ui.screens.LoginScreen
import ui.screens.LoginViewModel
import ui.screens.QuizListScreen
import ui.screens.QuizListViewModel
import ui.screens.QuizScreen
import ui.screens.QuizStatsScreen
import ui.screens.QuizStatsViewModel
import ui.screens.QuizViewModel
import ui.screens.SignupScreen
import ui.screens.SignupViewModel

enum class RoutesToScreen(val title: String) {
    Login("Login"),
    SignUp("SignUp"),
    Quiz("Quiz"),
    QuizStats("Quiz Statistics"),
    QuizList("Quiz List"),
    CreateQuiz("Create Quiz"),
    EditQuiz("Edit Quiz")
}
@Composable
fun App() {
    PreComposeApp {
        KoinContext {
            val navigator = rememberNavigator()
            var currentScreen by remember { mutableStateOf(RoutesToScreen.Login) }
            val scope = rememberCoroutineScope()

            scope.launch {
                navigator.currentEntry.collect {
                    currentScreen = RoutesToScreen.valueOf(it?.path?.substringBefore("/") ?: "Login")
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
                            initialRoute = RoutesToScreen.Login.name,
                        ) {
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

                                SignupScreen(
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
                                quizListViewModel.initQuizList()
                                val quizzes by quizListViewModel.quizzes.collectAsState()

                                QuizListScreen(
                                    quizModel = quizListViewModel,
                                    onAddQuizClick = { navigator.navigate(RoutesToScreen.CreateQuiz.name) },
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) }
                                ) { selectedQuiz ->
                                    navigator.navigate(RoutesToScreen.QuizStats.name + "/${selectedQuiz.id}")
                                }
                            }
                            scene(
                                route = RoutesToScreen.QuizStats.name + "/{quizId}",
                                navTransition = NavTransition(),
                            ) { backStackEntry ->
                                val quizId = backStackEntry.path<Int?>("quizId") ?: -1
                                val quizStatsViewModel = koinViewModel(vmClass = QuizStatsViewModel::class) {
                                    parametersOf(quizId)
                                }
                                quizStatsViewModel.initStats()
                                val quiz = quizStatsViewModel.quiz.value

                                if (quiz != null) {
                                    QuizStatsScreen(
                                        quiz = quiz,
                                        onBackClick = navigator::popBackStack,
                                        onEditQuizClick = {
                                            navigator.navigate(RoutesToScreen.EditQuiz.name + "/${quiz.id}")
                                        },
                                        onDeleteQuizClick = { quizStatsViewModel.deleteQuiz(quiz.id)},
                                        onLogoutClick = {
                                            navigator.navigate(RoutesToScreen.Login.name)
                                        },
                                        onQuizClick = {
                                            navigator.navigate(RoutesToScreen.Quiz.name + "/${quiz.id}")
                                        }
                                    )
                                } else {
                                    Text("There has been some error with quiz. Please email us regarding this matter techsup@flashq.com")
                                }
                            }

                            scene(
                                route = RoutesToScreen.CreateQuiz.name,
                                navTransition = NavTransition(),
                            ) {
                                val createQuizViewModel = koinViewModel(CreateQuizViewModel::class) {
                                    parametersOf(0)
                                }

                                CreateQuizScreen(
                                    quiz = createQuizViewModel.quiz.value,
                                    onBackClick = navigator::popBackStack,
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) },
                                    onAddCard = createQuizViewModel::addCard,
                                    onUpdateCard = createQuizViewModel::updateCard,
                                    onDeleteCard = createQuizViewModel::deleteCard,
                                    onSaveClick = { quizName ->
                                        createQuizViewModel.createQuiz(quizName)
                                        navigator.popBackStack()
                                    }
                                )
                            }

                            scene(
                                route = RoutesToScreen.EditQuiz.name + "/{quizId}",
                                navTransition = NavTransition(),
                            ) { backStackEntry ->
                                val quizId = backStackEntry.path<Int>("quizId")
                                val createQuizViewModel = koinViewModel(CreateQuizViewModel::class) {
                                    parametersOf(quizId)
                                }

                                CreateQuizScreen(
                                    quiz = createQuizViewModel.quiz.value,
                                    onBackClick = navigator::popBackStack,
                                    onLogoutClick = { navigator.navigate(RoutesToScreen.Login.name) },
                                    onAddCard = createQuizViewModel::addCard,
                                    onUpdateCard = createQuizViewModel::updateCard,
                                    onDeleteCard = createQuizViewModel::deleteCard,
                                    onSaveClick = {qname ->
                                        createQuizViewModel.updateQuiz(qname)
                                        navigator.popBackStack()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

