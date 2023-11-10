package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import models.LoginModel
import models.SignupModel
import screens.LoginScreen
import screens.SignUpScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val loginModel = LoginModel()
            val signupModel = SignupModel()

            // navigation host
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onLoginButtonClick = { loginModel.onLoginClick { navController.navigate("signup") } },
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

            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    // Create instances of LoginModel and SignupModel
    val loginModel = LoginModel()
    val signupModel = SignupModel()
    val dummyNavController = rememberNavController()

    LoginScreen(
        onLoginButtonClick = {},
        onNavigateToSignUp = { dummyNavController.navigate("signup")},
        loginModel = loginModel
    )

    SignUpScreen(
        onSignupButtonClick = {},
        onNavigateToLogin = {
            dummyNavController.popBackStack("login", inclusive = false)
        },
        signupModel = signupModel
    )

}
