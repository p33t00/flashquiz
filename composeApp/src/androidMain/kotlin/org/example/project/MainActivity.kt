package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import models.FlashcardModel
import models.LoginModel
import models.QuizModel
import models.SignupModel
import ui.screens.LoginScreen
import ui.screens.QuizListScreen
import ui.screens.QuizStatsScreen
import ui.screens.QuizViewScreen
import ui.screens.SignUpScreen
import ui.screens.CreateQuizScreen

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // instances
        val loginModel = LoginModel()
        val signupModel = SignupModel()
        val quizModel = QuizModel()
        val flashcardModel = FlashcardModel()


        // Launch Compose UI
        setContent {
            val navController = rememberNavController()

            // navigation host
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onLoginButtonClick = { loginModel.onLoginClick { navController.navigate("quizList") } },
                        onNavigateToSignUp = { navController.navigate("signup") },
                        loginModel = loginModel
                    )
                }

                composable("signup") {
                    SignUpScreen(
                        onSignupButtonClick = { /* add after signup here later */ },
                        onNavigateToLogin = { navController.navigate("login") },
                        signupModel = signupModel
                    )
                }

                composable("quizList") {
                    QuizListScreen(
                        quizModel = quizModel,
                        onAddQuizClick = { navController.navigate("createQuiz") },
                        onLogoutClick = { navController.navigate("login") },
                        onQuizClick = { selectedQuiz ->
                            navController.navigate("quizStats/${selectedQuiz.name}")
                        }
                    )
                }
                composable("quizStats/{quizName}") { backStackEntry ->
                    val quizName = backStackEntry.arguments?.getString("quizName")
                    val selectedQuiz = quizModel.quizList.value.find { it.name == quizName }

                    selectedQuiz?.let {
                        QuizStatsScreen(
                            quiz = it,
                            onBackClick = { navController.popBackStack() },
                            onEditQuizClick = {},
                            onDeleteQuizClick = {},
                            onLogoutClick = { navController.navigate("login") },
                            onQuizClick = {
                                navController.navigate("quiz/${selectedQuiz.name}")
                            }
                        )
                    }
                }
                composable("quiz/{quizName}") { backStackEntry ->
                    val quizName = backStackEntry.arguments?.getString("quizName")
                    val selectedQuiz = quizModel.quizList.value.find { it.name == quizName }

                    selectedQuiz?.let {
                        QuizViewScreen(
                            quiz = it,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }

                composable("createQuiz") {
                    CreateQuizScreen(
                        onLogoutClick = { navController.navigate("login") },
                        onSaveClick = { navController.popBackStack() },
                        onBackClick = { navController.popBackStack() }
                    )
                }
/*
                composable("createFlashcard") {
                    CreateFlashcardScreen(
                        onClick = { navController.popBackStack() }, // todo: This should save flashcard values
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable("flashcard/{flashcardName}") { backStackEntry ->
                    val flashcardName = backStackEntry.arguments?.getString("question")
                    val selectedFlashcard = flashcardModel.flashcardList.value.find { it.question == flashcardName }

                    selectedFlashcard?.let {
                        FlashcardViewScreen(
                            flashcard = it,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }

 */
            }
        }
    }


@Preview
@Composable
fun AppAndroidPreview() {
    // Preview with test data
    val loginModel = LoginModel()
    val signupModel = SignupModel()
    val quizModel = QuizModel()


    // Set up preview screens
    LoginScreen(
        onLoginButtonClick = {},
        onNavigateToSignUp = {},
        loginModel = loginModel
    )

    SignUpScreen(
        onSignupButtonClick = {},
        onNavigateToLogin = {},
        signupModel = signupModel
    )

    QuizListScreen(
        quizModel = quizModel,
        onAddQuizClick = {},
        onLogoutClick = {},
        onQuizClick = { }

    )

}
}
