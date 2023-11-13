package models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginModel {

    private val email = MutableStateFlow("")
    val emailState: StateFlow<String> = email

    private val password = MutableStateFlow("")
    val passwordState: StateFlow<String> = password


    //handle login
    fun onLoginClick(onNavigateToSignUp: () -> Unit) {
        println("Email: ${email.value}, Password: ${password.value}")

        // navigate to SignUpScreen
        onNavigateToSignUp.invoke()

    }
}
