/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.multigame.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.multigame.R
import com.example.multigame.data.DataSource.quantityOptions
import com.example.multigame.data.DataSource
import com.example.multigame.data.DataSource.game

/**
 * Composable that allows the user to select the desired cupcake quantity and expects
 * [onNextButtonClicked] lambda that expects the selected quantity and triggers the navigation to next screen
 */
@Composable
fun StartOrderScreen(
    gameoptions: List<String>,
    onFirstButtonClicked: () -> Unit,
    onSecondButtonClicked: () -> Unit,
    onThirdButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(16.dp).fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ///Image(
           /// painter = painterResource(R.drawable.app_name),
            ///contentDescription = null,
           /// modifier = Modifier.width(300.dp)
        ///)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.app_name), style = MaterialTheme.typography.h4)
        Spacer(modifier = Modifier.height(8.dp))
        SelectQuantityButton(
            labelResourceId = game.first(),
            onClick = { onFirstButtonClicked() }
        )
        SelectQuantityButton(
            labelResourceId = game.elementAt(1),
            onClick = { onSecondButtonClicked() }
        )
        SelectQuantityButton(
            labelResourceId = game.elementAt(2),
            onClick = { onThirdButtonClicked() }
        )
    }
}

/**
 * Customizable button composable that displays the [labelResourceId]
 * and triggers [onClick] lambda when this composable is clicked
 */
@Composable
fun SelectQuantityButton(
    labelResourceId: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(labelResourceId)
    }
}

