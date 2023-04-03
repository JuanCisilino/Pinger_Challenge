package com.frost.pingerchallenge.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.frost.pingerchallenge.ui.main.composables.MainScreen
import com.frost.pingerchallenge.ui.theme.PingerChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(
                this,
                state = viewModel.viewState.value,
                onEventSent = { event -> viewModel.setEvent(event) })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PingerChallengeTheme { }
}