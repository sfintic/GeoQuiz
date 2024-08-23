package com.example.geoquiz

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {
    // Mutable state to keep track of the current question index
    var currentIndex = mutableStateOf(0)
}