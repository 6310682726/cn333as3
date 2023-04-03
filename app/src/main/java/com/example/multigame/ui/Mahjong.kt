package com.example.multigame.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multigame.R
import com.example.multigame.data.aTeam
import com.example.multigame.data.bTeam

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {
    Column(modifier = modifier,horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement  = Arrangement.Center) {
        Row(modifier = Modifier.padding(1.dp)) {
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
        }
        Spacer(modifier = Modifier
            .height(16.dp)
            .weight(1f))
        Row(modifier = Modifier.padding(1.dp)) {
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
            diceroll()
        }
        Spacer(modifier = Modifier
            .height(16.dp)
            .weight(1f))
    }


}

@Composable
fun diceroll(modifier: Modifier = Modifier) {
    var result by remember { mutableStateOf( (1..3).random()) }
    val imageResource = when(result) {
        1 -> R.drawable.red
        2 -> R.drawable.blue
        else -> R.drawable.green
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = result.toString(),
                modifier = Modifier
                    .size(60.dp, 60.dp)
                    .clickable(onClick = { result = diceClick() })
                )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

fun diceClick(): Int{
    var randomA = (1..3).random()
    if(randomA == 1) {
        aTeam = aTeam + 1
    }
    return randomA
}
