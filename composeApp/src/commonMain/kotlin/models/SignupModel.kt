package models


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignupModel {
    private val username = MutableStateFlow("")
    val usernameState: StateFlow<String> = username

    private val email = MutableStateFlow("")
    val emailState: StateFlow<String> = email

    private val password = MutableStateFlow("")
    val passwordState: StateFlow<String> = password

    fun onSignupClick(onNavigateToLogin: () -> Unit) {

        println("Username: ${username.value}, Signup Email: ${email.value}, Password: ${password.value}")

        // navigate to LoginScreen
        onNavigateToLogin.invoke()
    }
}
