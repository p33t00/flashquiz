package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.Card


@Composable
fun QuizScreen(
    cards: List<Card>,
    onBackClick: () -> Unit,
    backToQuizListClick: () -> Unit,
    onQuizComplete: (String) -> Unit
) {
    var currentQuestionIndex by remember { mutableStateOf(0) }
    var correctAnswers by remember { mutableStateOf(0) }
    var answeredQuestions by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var score: String = ""

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                },
                backgroundColor = Color.White
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
                .padding(paddingValues)
                .padding(bottom = 16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (cards.isNotEmpty()) {
                if (currentQuestionIndex < cards.size) {
                    QuizQuestion(
                        card = cards[currentQuestionIndex],
                        onAnswerSelected = { answer ->
                            selectedAnswer = answer
                        }
                    )
                    LinearProgressIndicator(
                        progress = currentQuestionIndex.toFloat() / cards.size,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                            .height(10.dp),
                        color = Color(0xFFD6C1DF)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (selectedAnswer != null) {
                                if (selectedAnswer == cards[currentQuestionIndex].correctAnswer) {
                                    correctAnswers++
                                }
                                answeredQuestions++
                                currentQuestionIndex++
                                selectedAnswer = null
                            }
                        },
                        enabled = selectedAnswer != null,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF926EB4),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Next")
                    }
                } else {
                    score = "$correctAnswers/${cards.size}"
                    QuizComplete(
                        score = score,
                        backToQuizListClick = backToQuizListClick,
                        onQuizComplete = { onQuizComplete(score) }
                    )
                }
            } else {
                Text("No quiz questions available")
            }
        }
    }
}


@Composable
fun QuizComplete(
    score: String,
    backToQuizListClick: () -> Unit,
    onQuizComplete: () -> Unit
) {
    onQuizComplete()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Quiz Completed!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF926EB4),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "$score questions answered correctly",
            fontSize = 20.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = backToQuizListClick,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .clip(RoundedCornerShape(percent = 50)),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF926EB4))
        ) {
            Text(
                "Back to quiz list",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun QuizQuestion(
    card: Card,
    onAnswerSelected: (String) -> Unit
) {
    val shuffledAnswers = remember(card) {
        mutableListOf(
            card.correctAnswer,
            card.alternateOption1,
            card.alternateOption2,
            card.alternateOption3
        ).shuffled()
    }

    val selectedAnswer = remember { mutableStateOf<String?>(null) }

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
                text = card.text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            shuffledAnswers.forEach { answer ->
                QuizAnswer(
                    answer = answer,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = {
                        onAnswerSelected(it)
                        selectedAnswer.value = it
                    }
                )

                Spacer(modifier = Modifier.height(9.dp))
            }
        }
    }
}


@Composable
fun QuizAnswer(
    answer: String,
    selectedAnswer: MutableState<String?>,
    onAnswerSelected: (String) -> Unit
) {
    Button(
        onClick = {
            onAnswerSelected(answer)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selectedAnswer.value == answer)
                Color(0xFFD6C1DF) else Color.White
        )
    ) {
        Text(
            text = answer,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = if (selectedAnswer.value == answer)
                Color.White else Color.Black
        )
    }
}

