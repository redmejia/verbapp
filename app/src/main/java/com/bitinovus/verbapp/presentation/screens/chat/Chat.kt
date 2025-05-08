package com.bitinovus.verbapp.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bitinovus.verbapp.presentation.components.bubble.Bubble
import com.bitinovus.verbapp.presentation.components.prompt.PromptTextField
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBackground
import com.bitinovus.verbapp.presentation.ui.theme.senderBubble

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chat(
    contentPadding: PaddingValues,
) {
    var textFieldValue by remember { mutableStateOf("") }

    val messageList = listOf<Messages>(
        Messages(
            message = "this ", id = 1
        ),
        Messages(message = "Dos", id = 2),
        Messages(message = "meeeeee", id = 3),
        Messages(message = "Uno", id = 4),
        Messages(message = "Udasdsadno", id = 5),
        Messages(message = "Uno", id = 6),
        Messages(
            message = "this is test of long text, " +
                    "this is test of long text,  this is test of long" +
                    " text this is test of long text,  this is test of long text ",
            id = 7
        ),
        Messages(message = "Udasdasno", id = 8),
        Messages(message = "Uno", id = 9),
        Messages(message = "Undasdaso", id = 10),
        Messages(message = "Undasdsao", id = 11),
        Messages(message = "Uno", id = 12),
        Messages(message = "Udasdsano", id = 13),
        Messages(message = "Uewqeqwno", id = 14),
        Messages(message = "Undasdaso", id = 15),
    )

    Box(
        modifier = Modifier
            .background(color = PrimaryBackground)
            .imePadding()
            .systemBarsPadding()
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        LazyColumn(
            modifier = Modifier
                .windowInsetsPadding(WindowInsets.systemBars)
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = contentPadding,
            reverseLayout = true,
        ) {
            item {
                // reverse space between PromptTextField and Bubble Text
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(items = messageList, key = { it.id }) {
                val isSender = it.id % 2 != 0
                Bubble(
                    backgroundColor = if (isSender) senderBubble else PrimaryGray00,
                    isSender = isSender,
                    contentText = {
                        Text(
                            text = it.message,
                            color = if (isSender) Color.White else Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            PromptTextField(
                modifier = Modifier
                    .padding(vertical = 2.dp, horizontal = 8.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(50.dp)),
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                placeHolder = { Text(text = "Message...") },
                trailingIcon = {}
            )
        }
    }
}


data class Messages(
    val message: String,
    val id: Int,
)
