package ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import models.Question
import models.QuizModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CreateQuizScreen(
    quizModel: QuizModel,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit,
) {

    var quizName by remember { mutableStateOf("") }
    var addQuestion by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
    ) {

        IconButton(onClick = onBackClick) {
            Icon(imageVector = Icons.Default.ArrowBack,
                contentDescription = null)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            Spacer(modifier = Modifier.height(20.dp))


            Text(
                text = "Create Quiz",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .wrapContentSize(Alignment.TopCenter)
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = quizName,
                onValueChange = { quizName = it },
                label = { Text("Quiz name") }
            )

        }

        // add flashcard button
        FloatingActionButton(
            onClick = { addQuestion = true },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            backgroundColor = Color(0xFF926EB4),
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(26.dp)
            )
        }

        // Save quiz button
        FloatingActionButton(
            onClick = onSaveClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            backgroundColor = Color(0xFF926EB4),
        ) {
            Text("Save", color = Color.White)
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
                .clickable {
                    onLogoutClick.invoke()
                }
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle, //icon to be changed
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

    }

    if (addQuestion) {
        CreateQuestion(
            onSave = {
                quizModel.addQuestion(it)
                addQuestion = false
            },
            onDismissRequest = {
                addQuestion = false
            }
        )
    }
}

@Composable
fun CreateQuestion(
    onDismissRequest: () -> Unit,
    onSave: () -> Unit,
) {

    var question by remember { mutableStateOf(Question("", "", "", "", "")) }


    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 60.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(
                    text = "Question",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentSize(Alignment.TopCenter)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = question.text,
                    onValueChange = { question.text = it },
                    label = { Text("Question") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = question.correctAnswer,
                    onValueChange = { question.correctAnswer = it },
                    label = { Text("Correct Answer") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = question.alternateOption1,
                    onValueChange = { question.alternateOption1 = it },
                    label = { Text("Alternate Option 1") }
                )
                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = question.alternateOption2,
                    onValueChange = { question.alternateOption2 = it },
                    label = { Text("Alternate Option 2") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = question.alternateOption3,
                    onValueChange = { question.alternateOption3 = it },
                    label = { Text("Alternate Option 3") }
                )

                Spacer(modifier = Modifier.width(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = { onSave.invoke() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
