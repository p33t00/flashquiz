package models

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginModel {

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState


    //handle login button click
    fun onLoginClick(onNavigateToSignUp: () -> Unit) {
        // Add your login logic here
        // For simplicity, just print the entered email and password
        println("Email: ${_emailState.value}, Password: ${_passwordState.value}")

        // navigate to SignUpScreen
        onNavigateToSignUp.invoke()
    }
}
