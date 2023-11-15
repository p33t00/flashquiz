package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.LoginModel


@Composable
fun LoginScreen(  onLoginButtonClick: () -> Unit,
                  onNavigateToSignUp: () -> Unit,
                  loginModel: LoginModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "FlashQuiz",
            fontSize = 42.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color =  Color(0xFF926EB4),
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        Text(
            text = "Login",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 30.dp)
        )


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        // login button
        Button(
            onClick = { onLoginButtonClick.invoke() },
            modifier = Modifier
                .width(200.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(percent = 50)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor =  Color(0xFF926EB4),
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(
                text = "Log in",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 14.sp,
            )
        }


        // sign up button
        Button(
            onClick = { onNavigateToSignUp.invoke() },
            modifier = Modifier.fillMaxWidth()
                .padding(top = 16.dp)
                .clip(MaterialTheme.shapes.medium),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                contentColor =  Color(0xFF926EB4),
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            contentPadding = PaddingValues(16.dp),
        ) {
            Text(
                text = "Don't have an account? Sign up now",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Color(0xFF926EB4)
            )
        }
    }
}
