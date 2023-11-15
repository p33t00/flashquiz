package ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.Quiz

@Composable
fun QuizStatsScreen(
    quiz: Quiz,
    onBackClick: () -> Unit,
    onEditQuizClick: () -> Unit,
    onDeleteQuizClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onQuizClick: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Quiz: ${quiz.name}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
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
                            onEditQuizClick() }) {
                            Text("Edit Quiz")
                        }
                        DropdownMenuItem(onClick = {
                            onDeleteQuizClick() }) {
                            Text("Delete Quiz")
                        }
                        DropdownMenuItem(onClick = {
                            onLogoutClick() }) {
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
                .padding(paddingValues)
        ) {


            // add statistics display later here

            // start quiz button
            FloatingActionButton(
                onClick = onQuizClick,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(20.dp)
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
}
