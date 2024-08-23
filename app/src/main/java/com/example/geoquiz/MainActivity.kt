//Solo Fintic 1158134
package com.example.geoquiz

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalContext
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {

    // Question data model
    data class Question(val text: String, val isAnswerTrue: Boolean)

    // List of questions
    private val questionBank = listOf(
        Question("The Pacific Ocean is the largest ocean on Earth.", true),
        Question("Australia is the smallest continent.", true),
        Question("The Great Wall of China is visible from space.", false),
        Question("Mount Everest is the tallest mountain in the world.", true),
        Question("The Amazon River is the longest river in the world.", false)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoQuizTheme {
                QuizApp(questionBank)
            }
        }
    }
}

@Composable
fun QuizApp(questionBank: List<MainActivity.Question>) {
    val quizViewModel: QuizViewModel = viewModel()
    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        QuizScreen(
            question = questionBank[quizViewModel.currentIndex.value],
            onNextClicked = {
                quizViewModel.currentIndex.value = (quizViewModel.currentIndex.value + 1) % questionBank.size
            },
            onPreviousClicked = {
                quizViewModel.currentIndex.value = (quizViewModel.currentIndex.value - 1 + questionBank.size) % questionBank.size
            },
            onCheatClicked = {
                val intent = Intent(context, CheatActivity::class.java)
                intent.putExtra("ANSWER", questionBank[quizViewModel.currentIndex.value].isAnswerTrue)
                context.startActivity(intent)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun QuizScreen(
    question: MainActivity.Question,
    onNextClicked: () -> Unit,
    onPreviousClicked: () -> Unit,
    onCheatClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State to hold the result of the user's answer
    var resultText by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display the current question
        Text(text = question.text)

        Spacer(modifier = Modifier.height(16.dp))

        // TRUE and FALSE buttons
        Row {
            Button(onClick = {
                resultText = if (question.isAnswerTrue) "Correct!" else "Incorrect!"
            }) {
                Text(text = "TRUE")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                resultText = if (!question.isAnswerTrue) "Correct!" else "Incorrect!"
            }) {
                Text(text = "FALSE")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display the result (Correct or Incorrect)
        Text(text = resultText)

        Spacer(modifier = Modifier.height(16.dp))

        // CHEAT button
        Button(onClick = onCheatClicked) {
            Text(text = "CHEAT")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Navigation buttons with icons
        Row {
            Button(onClick = {
                resultText = ""
                onPreviousClicked()
            }) {
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_left),
                    contentDescription = "Previous"
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                resultText = ""
                onNextClicked()
            }) {
                Text(text = "Next")
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_right),
                    contentDescription = "Next"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    GeoQuizTheme {
        QuizScreen(
            question = MainActivity.Question("Is Australia the smallest continent?", true),
            onNextClicked = {},
            onPreviousClicked = {},
            onCheatClicked = {}
        )
    }
}
