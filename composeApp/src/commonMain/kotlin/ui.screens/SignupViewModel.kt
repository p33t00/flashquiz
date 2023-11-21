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

  /*  private val username = MutableStateFlow("")
    val usernameState: StateFlow<String> = username

    private val email = MutableStateFlow("")
    val emailState: StateFlow<String> = email

    private val password = MutableStateFlow("")
    val passwordState: StateFlow<String> = password */

    fun onSignupClick(onNavigateToLogin: () -> Unit) {

     //   println("Username: ${username.value}, Signup Email: ${email.value}, Password: ${password.value}")

        // navigate to LoginScreen
        onNavigateToLogin.invoke()
    }
}