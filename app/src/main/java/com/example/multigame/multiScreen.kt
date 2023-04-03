package com.example.multigame

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.multigame.data.DataSource
import com.example.multigame.ui.theme.MultiGameTheme
import com.example.multigame.ui.StartOrderScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.multigame.ui.OrderViewModel
import com.example.multigame.ui.GuessLayout
import com.example.multigame.ui.GameScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.multigame.ui.DiceWithButtonAndImage

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Flavor(title = R.string.choose_flavor),
    Pickup(title = R.string.choose_pickup_date),
    Summary(title = R.string.order_summary)
}

@Composable
fun MultiAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { androidx.compose.material.Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MultiApp(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )

    Scaffold(
        topBar = {
            MultiAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    gameoptions = DataSource.game,
                    onFirstButtonClicked = {
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    onSecondButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    onThirdButtonClicked = {
                        navController.navigate(CupcakeScreen.Pickup.name)
                    }

                )
            }
            composable(route = CupcakeScreen.Flavor.name) {
                val context = LocalContext.current
                GuessLayout()
            }
            composable(route = CupcakeScreen.Summary.name) {
                val context = LocalContext.current
                GameScreen()
            }
            composable(route = CupcakeScreen.Pickup.name) {
                val context = LocalContext.current
                DiceWithButtonAndImage()
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}