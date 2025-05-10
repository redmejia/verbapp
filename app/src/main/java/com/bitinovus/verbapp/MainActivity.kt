package com.bitinovus.verbapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.bitinovus.verbapp.presentation.screens.chat.Chat
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBlack00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.verbapp.presentation.ui.theme.VerbappTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(5000)
            keepSplashScreen = false
        }

        enableEdgeToEdge()
        setContent {
            VerbappTheme {
                Scaffold(
                    contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
                    topBar = {
                        TopAppBar(
                            windowInsets = TopAppBarDefaults.windowInsets,
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = PrimaryWhite00,
                                titleContentColor = PrimaryBlack00,
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
