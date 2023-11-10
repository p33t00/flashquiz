package models


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SignupModel {
    private val _usernameState = MutableStateFlow("")
    val usernameState: StateFlow<String> = _usernameState

    private val _emailState = MutableStateFlow("")
    val emailState: StateFlow<String> = _emailState

    private val _passwordState = MutableStateFlow("")
    val passwordState: StateFlow<String> = _passwordState

    fun onSignupClick(onNavigateToLogin: () -> Unit) {

        println("Username: ${_usernameState.value}, Signup Email: ${_emailState.value}, Password: ${_passwordState.value}")

        // navigate to LoginScreen
        onNavigateToLogin.invoke()
    }
}
