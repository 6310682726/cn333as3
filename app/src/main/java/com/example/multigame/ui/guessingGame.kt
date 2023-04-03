package com.example.multigame.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multigame.R

@Composable
fun GuessLayout(
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
) {
    var numGuess by remember { mutableStateOf("") }
    val iNum: Int = 700

    var guess = numGuess.toIntOrNull() ?: 0
    var tip = hint(iNum, guess)
    Column(
        verticalArrangement = Arrangement.Top,
    ) {
        ComposableTitle(
            title = stringResource(R.string.title),
            backgroundColor = Color.Blue,
            fontSize = 30,
            modifier = Modifier.weight(0.35f)
        )
        ComposableInfoCard(
            title = stringResource(R.string.signature_text),
            backgroundColor = Color.White,
            fontSize = 20,
            modifier = Modifier.weight(1f)
        )
        EditNumberField(
            value = numGuess,
            onValueChanged = { numGuess = it },
            backgroundColor = Color.White,
            modifier = Modifier.weight(1f)
        )
        //Spacer(Modifier.height(20.dp))
        MyButton(
            tip = tip,
            backgroundColor = Color.White,
            modifier = Modifier.weight(1f)
        )

    }

}

@Composable
private fun ComposableInfoCard(
    title: String,
    backgroundColor: Color,
    fontSize: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
private fun ComposableTitle(
    title: String,
    backgroundColor: Color,
    fontSize: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    value: String,
    onValueChanged: (String) -> Unit,
    backgroundColor: Color,
    modifier: Modifier = Modifier

) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = value,
            onValueChange = onValueChanged,
            label = { Text(stringResource(R.string.cost_of_service)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}

/**
 * Calculates the tip based on the user input and format the tip amount
 * according to the local currency and display it onscreen.
 * Example would be "$10.00".
 */
private fun hint(iNum: Int, guess: Int //tipPercent: Double = 15.0
): String {
    return  if ( guess == null) return "null"
    else if ( guess < iNum && guess >= 1) "Hint: it's higher!"
    else if ( guess == 0) ""
    else if ( guess == iNum) "correct"
    else "Hint: it's lower"
    /*val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip)*/
}

@Composable
fun MyButton(tip: String, backgroundColor: Color,
             modifier: Modifier = Modifier) {
    var status by remember {
        mutableStateOf( 1)
    }
    val showHint = when(status) {
        1 -> ""
        0 -> gameSetting(status = "start", tip)
        else -> ""
    }
    val (points, setPoints) = remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = showHint,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = { if(status == 1) status = 0 else status = 1},
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = if(status == 0) "play again" else "start")
        }
    }
}

fun gameSetting(status: String, tip: String): String {
    return if ( status == "start") return tip
    else if ( status == "end" ) "your guess $status time"
    else if ( status == "re") ""
    else if ( status == "process") ""
    else ""
}

/* Image
@Composable
fun BirthdayGreetingWithImage(message: String, from: String, modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_launcher_background)
    // Create a box to overlap image and texts
    Box {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop ,
            modifier = Modifier.size(500.dp, 60.dp)
        )
        BirthdayGreetingWithText(message = message, from = from, modifier = modifier)
    }
}*/


