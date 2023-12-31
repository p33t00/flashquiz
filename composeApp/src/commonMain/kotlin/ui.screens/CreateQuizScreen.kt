package ui.screens

import RoutesToScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import domain.model.Card
import domain.model.CardStateIntent
import domain.model.Quiz

@Composable
fun CreateQuizScreen(
    quiz: Quiz,
    onLogoutClick: () -> Unit,
    onBackClick: () -> Unit,
    onAddCard: (Card) -> Unit,
    onUpdateCard: (Card, Card) -> Unit,
    onDeleteCard: (Card) -> Unit,
    onSaveClick: (String) -> Unit,
) {
    var quizName by remember { mutableStateOf(quiz.name) }
    var addCard by remember { mutableStateOf(false) }
    var updateCard by remember { mutableStateOf(false) }
    var cardToBeUpdated: Card? by remember { mutableStateOf(null) }
    val screenName = if (quiz.id > 0) RoutesToScreen.EditQuiz.title else RoutesToScreen.CreateQuiz.title
    var isNameEmpty by remember { mutableStateOf(false) }

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
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = screenName,
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
                onValueChange = {
                    quizName = it
                    isNameEmpty = quizName.isEmpty()
                },
                label = { Text("Quiz name") },
                isError = isNameEmpty
                //isError = quizName.isEmpty()
            )
            LazyColumn {
                itemsIndexed(quiz.cards) { _, card ->
                    if (card.stateIntent != CardStateIntent.Delete) {
                        CardRow(
                            card = card,
                            onClick = {
                                cardToBeUpdated = card
                                updateCard = true
                            },
                            onDeleteClick = { onDeleteCard(card) }
                        )
                    }
                }
            }
        }

        // add flashcard button
        FloatingActionButton(
            onClick = { addCard = true },
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
            onClick = {
                if (quizName.isNotEmpty()) {
                    onSaveClick(quizName)
                } else null
            },
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

    if (addCard) {
        CreateQuestion(
            onSave = {card ->
                onAddCard(card)
                addCard = false
            },
            onDismissRequest = {
                addCard = false
            }
        )
    }

    if (updateCard && cardToBeUpdated != null) {
        CreateQuestion(
            card = cardToBeUpdated,
            onSave = {card ->
                onUpdateCard(card, cardToBeUpdated as Card)
                updateCard = false
                cardToBeUpdated = null
            },
            onDismissRequest = {
                updateCard = false
                cardToBeUpdated = null
            }
        )
    }
}

@Composable
fun CreateQuestion(
    card: Card? = null,
    onSave: (Card) -> Unit,
    onDismissRequest: () -> Unit
) {
    val scrollState = rememberScrollState()
    var questionText by remember { mutableStateOf("") }
    var correctAnswer by remember { mutableStateOf("") }
    var alternate1 by remember { mutableStateOf("") }
    var alternate2 by remember { mutableStateOf("") }
    var alternate3 by remember { mutableStateOf("") }
    var isTextEmpty by remember { mutableStateOf(false) }
    var isAnswerEmpty by remember { mutableStateOf(false) }
    var isAlt1Empty by remember { mutableStateOf(false) }
    var isAlt2Empty by remember { mutableStateOf(false) }
    var isAlt3Empty by remember { mutableStateOf(false) }

    if (card != null) {
        questionText = card.text
        correctAnswer = card.correctAnswer
        alternate1 = card.alternateOption1
        alternate2 = card.alternateOption2
        alternate3 = card.alternateOption3
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 60.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState),
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
                    value = questionText,
                    onValueChange = {
                        questionText = it
                        isTextEmpty = questionText.isEmpty()
                    },
                    label = { Text("Question") },
                    isError = isTextEmpty
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = correctAnswer,
                    onValueChange = {
                        correctAnswer = it
                        isAnswerEmpty = correctAnswer.isEmpty()
                    },
                    label = { Text("Correct Answer") },
                    isError = isAnswerEmpty
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = alternate1,
                    onValueChange = {
                        alternate1 = it
                        isAlt1Empty = alternate1.isEmpty()
                    },
                    label = { Text("Alternate Option 1") },
                    isError = isAlt1Empty
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = alternate2,
                    onValueChange = {
                        alternate2 = it
                        isAlt2Empty = alternate2.isEmpty()
                    },
                    label = { Text("Alternate Option 2") },
                    isError = isAlt2Empty
                )
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    value = alternate3,
                    onValueChange = {
                        alternate3 = it
                        isAlt3Empty = alternate3.isEmpty()
                    },
                    label = { Text("Alternate Option 3") },
                    isError = isAlt3Empty
                )
                Spacer(modifier = Modifier.width(4.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    Button(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF926EB4),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            onSave(
                                Card(
                                    0,
                                    questionText,
                                    correctAnswer,
                                    alternate1,
                                    alternate2,
                                    alternate3
                                )
                            )
                        },
                        enabled = questionText.isNotEmpty()
                                && correctAnswer.isNotEmpty()
                                && alternate1.isNotEmpty()
                                && alternate2.isNotEmpty()
                                && alternate3.isNotEmpty(),
                        modifier = Modifier.padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF926EB4),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}


@Composable
fun CardRow(
    card: Card,
    onClick: (Card) -> Unit,
    onDeleteClick: (Card) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick.invoke(card)
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
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = card.text,
                fontSize = 16.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { onDeleteClick(card) },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Delete",
                    tint = Color.Black
                )
            }
        }
    }
}