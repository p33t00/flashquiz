package screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Quiz
import models.QuizModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuizListScreen(
    quizModel: QuizModel,
    onAddQuizClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onQuizClick: (Quiz) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var deleteConfirmation by remember { mutableStateOf(false) }
    var checkedQuizzes by remember { mutableStateOf(mutableSetOf<Quiz>()) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome to FlashQuiz",
                fontSize = 25.sp,
                color = Color(0xFF6A1B9A),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn {
                items(quizModel.quizList.value.distinct()) { quiz ->
                    QuizItemRow(
                        quiz = quiz,
                        onQuizClick = onQuizClick,
                        onDeleteClick = {
                            deleteConfirmation = true
                            checkedQuizzes.add(quiz)
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onAddQuizClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
                backgroundColor = Color(0xFF6A1B9A)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {


            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Logout",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }


        if (deleteConfirmation) {
            AlertDialog(
                onDismissRequest = {
                    deleteConfirmation = false
                    checkedQuizzes.clear()
                },
                title = {
                    Text(text = "Are you sure you want to delete?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            deleteConfirmation = false
                            checkedQuizzes.forEach { quizModel.removeQuiz(it) }
                            checkedQuizzes.clear()
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            deleteConfirmation = false
                            checkedQuizzes.clear()
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}
@Composable
fun QuizItemRow(
    quiz: Quiz,
    onDeleteClick: () -> Unit,
    onQuizClick: (Quiz) -> Unit
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onQuizClick.invoke(quiz)
            }
            .shadow(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {


            Text(
                text = quiz.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
