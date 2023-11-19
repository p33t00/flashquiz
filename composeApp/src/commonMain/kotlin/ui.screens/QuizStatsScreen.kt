package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Quiz
import models.QuizModel
import models.QuizStat

@Composable
fun QuizStatsScreen(
    quiz: Quiz,
    quizModel: QuizModel,
    onBackClick: () -> Unit,
    onEditQuizClick: () -> Unit,
    onDeleteQuizClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onQuizClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz: ${quiz.name}",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().padding(14.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() },
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { menuExpanded = true }
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
                        modifier = Modifier
                            .padding(end = 8.dp)
                    ) {
                        DropdownMenuItem(onClick = {
                            onEditQuizClick()
                        }) {
                            Text("Edit Quiz")
                        }
                        DropdownMenuItem(onClick = {
                            showDialog = true
                        }) {
                            Text("Delete Quiz")
                        }

                        DropdownMenuItem(onClick = {
                            onLogoutClick()
                        }) {
                            Text("Logout")
                        }
                    }
                },
                backgroundColor = Color.White
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
            //display quiz statistics if available
            if (quiz.stats.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    items(quiz.stats) { stat ->
                        QuizStatItem(stat)
                    }
                }
            } else {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                Text(
                    text = "No quiz stats available.",
                    fontSize = 17.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            }

        // Start quiz button
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            FloatingActionButton(
                onClick = onQuizClick,
                modifier = Modifier
                    .widthIn(150.dp),
                backgroundColor = Color(0xFFD6C1DF),
            ) {
                Text(
                    text = "Start Quiz",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }

        //delete confirmation dialog
        if (showDialog) {
            DeleteConfirmationDialog(
                onConfirm = {
                    // Remove quiz from the model
                    quizModel.removeQuiz(quiz)
                    showDialog = false

                    // Navigate back to quiz list
                    onBackClick()
                },
                onDismiss = {
                    showDialog = false
                }
                )
            }
        }


@Composable
fun QuizStatItem(stat: QuizStat) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "${stat.score}/${stat.totalQuestions} scored on ${stat.date}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }
    }
}

