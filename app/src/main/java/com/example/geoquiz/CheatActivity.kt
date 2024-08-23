package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.geoquiz.ui.theme.GeoQuizTheme

class CheatActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoQuizTheme {
                val answer = remember { intent.getBooleanExtra("ANSWER", false) }
                CheatScreen(answer)
            }
        }
    }
}

@Composable
fun CheatScreen(answer: Boolean) {
    Text(text = if (answer) "The answer is TRUE." else "The answer is FALSE.")
}

@Preview(showBackground = true)
@Composable
fun CheatScreenPreview() {
    GeoQuizTheme {
        CheatScreen(true)
    }
}
