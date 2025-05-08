package com.bitinovus.verbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.bitinovus.verbapp.presentation.screens.chat.Chat
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBackground
import com.bitinovus.verbapp.presentation.ui.theme.VerbappTheme
import com.bitinovus.verbapp.presentation.ui.theme.TextColor

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            VerbappTheme {
                Scaffold(
                    contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
                    topBar = {
                        TopAppBar(
                            windowInsets = TopAppBarDefaults.windowInsets,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PrimaryBackground,
                                titleContentColor = TextColor,
                            ),
                            title = {
                                Text("Verbapp")
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                        Chat(contentPadding = innerPadding)
                }
            }
        }
    }
}
