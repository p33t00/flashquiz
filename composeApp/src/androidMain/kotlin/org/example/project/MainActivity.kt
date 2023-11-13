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
import models.LoginModel
import models.Quiz
import models.QuizModel
import models.SignupModel
import screens.LoginScreen
import screens.QuizListScreen
import screens.QuizViewScreen
import screens.SignUpScreen

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // instances
        val loginModel = LoginModel()
        val signupModel = SignupModel()
        val quizModel = QuizModel()

        // for testing
        val dummyQuizzes = listOf(
            Quiz("Ml math basics"),
            Quiz("Spanish level 2"),
            Quiz("Standard deviation basics"),
        )

        dummyQuizzes.forEach { quizModel.addQuiz(it) }

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
                        onAddQuizClick = { /* navigate to add quiz screen */ },
                        onLogoutClick = { navController.navigate("login") },
                                onQuizClick = { selectedQuiz ->
                            navController.navigate("quiz/${selectedQuiz.name}")
                        }
                    )
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
