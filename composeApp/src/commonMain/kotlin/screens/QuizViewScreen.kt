package screens

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import models.Quiz

@Composable
fun QuizViewScreen(quiz: Quiz, onBackClick: () -> Unit) {


        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null)
        }

    }

