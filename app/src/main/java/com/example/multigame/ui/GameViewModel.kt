/*
 * Copyright (c)2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.multigame.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.multigame.data.MAX_NO_OF_WORDS
import com.example.multigame.data.SCORE_INCREASE
import com.example.multigame.data.allWords
import com.example.multigame.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * ViewModel containing the app data and methods to process the data
 */
class GameViewModel : ViewModel() {

    // Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set
    var randomQuiz by mutableStateOf(0)
        private set
    var selectedVal by mutableStateOf("")

    // Set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()
    private var usedQuiz: MutableSet<String> = mutableSetOf()
    private var usedRandom: MutableSet<Int> = mutableSetOf()
    private lateinit var currentWord: String
    private lateinit var currentQuiz: String
    fun randomQ(): Int{
        randomQuiz = (0..10).random()
        if (usedRandom.contains(randomQuiz)) {return randomQ()}
        usedRandom.add(randomQuiz)
        return  randomQuiz
    }

    init {
        resetGame()
    }

    /*
     * Re-initializes the game data to restart the game.
     */
    fun resetGame() {
        usedWords.clear()
        usedQuiz.clear()
        usedRandom.clear()
        _uiState.value = GameUiState(currentQuiz = pickRandomWordAndShuffle())
    }

    /*
     * Update the user's guess
     */
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    /*
     * Checks if the user's guess is correct.
     * Increases the score accordingly.
     */
    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isGuessedQuizWrong = true)
            }
        }
        // Reset user guess
        updateUserGuess("")
    }

    /*
     * Skip to next word
     */
    fun skipWord() {
        updateQuizState(_uiState.value.score)
        // Reset user guess
        updateUserQuiz("")
    }

    /*
     * Picks a new currentWord and currentScrambledWord and updates UiState according to
     * current game state.
     */
    private fun updateGameState(updatedScore: Int) {
        if (usedWords.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedQuizWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedQuizWrong = false,
                    currentQuiz = pickRandomWordAndShuffle(),
                    currentQuizCount = currentState.currentQuizCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        // Scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return word
    }

    private fun pickRandomWordAndShuffle(): String {
        // Continue picking up a new random word until you get one that hasn't been used before
        currentWord = allWords.elementAt(randomQ())
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }





    fun valSeclect(select: String) {
        selectedVal = select
    }

    fun updateUserQuiz(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserQuiz() {
        if (selectedVal.equals(answer.elementAt(randomQuiz), ignoreCase = true)) {
            // User's guess is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateQuizState(updatedScore)
        } else {
            val updatedScore = _uiState.value.score.plus(0)
            updateQuizState(updatedScore)
        }
    }

    private fun updateQuizState(updatedScore: Int) {
        if (usedQuiz.size == MAX_NO_OF_WORDS){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedQuizWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isGuessedQuizWrong = false,
                    currentQuiz = pickRandomWordAndShuffle(),
                    currentQuizCount = currentState.currentQuizCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    fun RandomQuiz(): List<String> {
        var data = choice0
        var index = randomQuiz
        if (index == 0) { data = choice0 }
        else if (index == 1) { data = choice1}
        else if (index == 2) { data = choice2}
        else if (index == 3) { data = choice3}
        else if (index == 4) { data = choice4}
        else if (index == 5) { data = choice5}
        else if (index == 6) { data = choice6}
        else if (index == 7) { data = choice7}
        else if (index == 8) { data = choice8}
        else if (index == 9) { data = choice9}
        else { data = choice10}
        usedQuiz.add(currentWord)
        return data.shuffled()
    }

    //private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    //val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow(

    /**
     * Set the [desiredFlavor] of cupcakes for this order's state.
     * Only 1 flavor can be selected for the whole order.
     */
    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    /**
     * Set the [pickupDate] for this order's state and update the price
     */
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    /**
     * Returns the calculated price based on the order details.
     */
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        return "formattedPrice"
    }

    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}

