package ui.screens

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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
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
    var menuExpanded by remember { mutableStateOf(false) }
    var checkedQuizzes by remember { mutableStateOf(mutableSetOf<Quiz>()) }
    var showDialog by remember { mutableStateOf(false) }
    var showSnackbar by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "FlashQuiz",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF926EB4),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                },
                actions = {
                    IconButton(
                        onClick = { menuExpanded = true },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        DropdownMenuItem(onClick = {
                            onLogoutClick.invoke()
                        }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Logout")
                        }
                        DropdownMenuItem(onClick = {
                            if (checkedQuizzes.isNotEmpty()) {
                                showDialog = true
                            } else {
                                showSnackbar = true
                                snackbarMessage = "Please select a quiz first to delete"
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Black
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Delete")
                        }
                    }
                },
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
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
                    text = "Quiz List",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentSize(Alignment.Center)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (quizModel.quizList.value.isEmpty()) {
                    Text("No quiz available", fontWeight = FontWeight.Bold)
                } else {
                    LazyColumn {
                        items(quizModel.quizList.value.distinct()) { quiz ->
                            QuizItemRow(
                                quiz = quiz,
                                onQuizClick = onQuizClick,
                                onCheckboxChange = { isChecked ->
                                    if (isChecked) {
                                        checkedQuizzes.add(quiz)
                                    } else {
                                        checkedQuizzes.remove(quiz)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // add quiz button
            FloatingActionButton(
                onClick = onAddQuizClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                backgroundColor = Color(0xFFD6C1DF),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }



              // Snack bar to display message
            if (showSnackbar) {
                LaunchedEffect(showSnackbar) {
                    delay(2000)
                    showSnackbar = false
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x99000000))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Snackbar(
                        action = {
                            TextButton(
                                onClick = { showSnackbar = false }
                            ) {
                                Text("Dismiss", color = Color.White)
                            }
                        }
                    ) {
                        Text(snackbarMessage, color = Color.White)
                    }
                }
            }
        }

        // Delete confirmation for selected quizzes
        if (showDialog) {
            DeleteConfirmationDialog(
                onConfirm = {
                    checkedQuizzes.forEach {
                        quizModel.removeQuiz(it)
                    }
                    checkedQuizzes.clear()
                    showDialog = false
                },
                onDismiss = {
                    checkedQuizzes.clear()
                    showDialog = false
                }
            )
        }
    }
}
@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = {
            onDismiss.invoke()
        },
        title = {
            Text(text = "Are you sure you want to delete?")
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFD6C1DF),
                    contentColor = Color.White
                )
            ) {
                Text("Yes", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss.invoke()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFD6C1DF),
                    contentColor = Color.White
                )
            ) {
                Text("No", color = Color.White)
            }
        }
    )
}


@Composable
fun QuizItemRow(
    quiz: Quiz,
    onCheckboxChange: (Boolean) -> Unit,
    onQuizClick: (Quiz) -> Unit
) {
    var isChecked by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .clickable {
                onQuizClick.invoke(quiz)
            }
            .shadow(4.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    onCheckboxChange(it)
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = quiz.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
