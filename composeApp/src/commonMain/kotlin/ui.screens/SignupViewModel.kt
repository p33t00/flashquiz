package ui.screens


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class SignupViewModel : ViewModel() {

    private val _signupState = MutableStateFlow(SignupState())
    val signupState = _signupState.asStateFlow()

    data class SignupState(
        val username: String = "",
        val email: String = "",
        val password: String = ""
    )

    fun setUsername(username: String) {
        _signupState.value = _signupState.value.copy(username = username)
    }

    fun setEmail(email: String) {
        _signupState.value = _signupState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _signupState.value = _signupState.value.copy(password = password)
    }

    fun onSignupClick(onNavigateToLogin: () -> Unit) {

     //   println("Username: ${username.value}, Signup Email: ${email.value}, Password: ${password.value}")

        // navigate to LoginScreen
        onNavigateToLogin.invoke()
    }
}