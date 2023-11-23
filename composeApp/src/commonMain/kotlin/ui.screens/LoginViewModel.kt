package ui.screens

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow(LoginState())
    val loginState = _loginState.asStateFlow()

    data class LoginState(
        val email: String = "",
        val password: String = ""
    )

    fun setEmail(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    fun setPassword(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }

   /*  private val email = MutableStateFlow("")
    val emailState: StateFlow<String> = email

    private val password = MutableStateFlow("")
    val passwordState: StateFlow<String> = password  */


    //handle login
    fun onLoginClick(onNavigateToSignUp: () -> Unit) {


    // add logic


        // navigate to SignUpScreen
        onNavigateToSignUp.invoke()


    }
}