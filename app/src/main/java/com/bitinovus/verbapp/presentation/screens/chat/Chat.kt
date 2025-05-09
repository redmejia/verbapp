package com.bitinovus.verbapp.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bitinovus.verbapp.presentation.components.bubble.Bubble
import com.bitinovus.verbapp.presentation.components.prompt.PromptTextField
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBackground
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import com.bitinovus.verbapp.presentation.ui.theme.senderBubble

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chat(
    contentPadding: PaddingValues,
) {
    var textFieldValue by remember { mutableStateOf("") }

    val messageList = listOf(
        ChatMessage.Sender(id = 1, subjectType = "Sender", message = "Hello! How are you?"),
        ChatMessage.Receiver(
            id = 2,
            subjectType = "Receiver",
            message = "I am good, thanks! What about you?"
        ),
        ChatMessage.Sender(
            id = 3,
            subjectType = "Sender",
            message = "Doing great! Just building a chat app."
        ),
        ChatMessage.Receiver(id = 4, subjectType = "Receiver", message = "Nice! That sounds fun."),
        ChatMessage.Sender(id = 5, subjectType = "Sender", message = "Yes, Kotlin is amazing."),
        ChatMessage.Receiver(id = 6, subjectType = "Receiver", message = "Totally agree!"),
        ChatMessage.Sender(id = 7, subjectType = "Sender", message = "Let's catch up soon."),
        ChatMessage.Receiver(
            id = 8,
            subjectType = "Receiver",
            message = "Sure thing! Looking forward to it."
        ),
        ChatMessage.Sender(id = 9, subjectType = "Sender", message = "See, you!"),
        ChatMessage.Sender(id = 10, subjectType = "Sender", message = "See, you!, hello, world."),
        ChatMessage.Sender(id = 11, subjectType = "Receiver", message = "ok"),
        ChatMessage.Sender(id = 12, subjectType = "Receiver", message = "ok"),
        ChatMessage.Sender(id = 13, subjectType = "Receiver", message = "ok"),
        ChatMessage.Sender(id = 14, subjectType = "Receiver", message = "ok"),
        ChatMessage.Sender(id = 15, subjectType = "Receiver", message = "ok"),
    ).reversed()

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .background(color = PrimaryBackground)
    ) {
        LazyColumn(
            modifier = Modifier
                .weight(1f),
             verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = contentPadding,
            reverseLayout = true,
        ) {
            items(items = messageList, key = { it.id }) {
                val isSender = it.subjectType == "Sender"
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
        PromptTextField(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .imePadding()
                .fillMaxWidth()
                .clip(RoundedCornerShape(50.dp)),
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            placeHolder = { Text(text = "Message...") },
            trailingIcon = {}
        )
    }
}


sealed class ChatMessage {
    abstract val subjectType: String
    abstract val id: Int
    abstract val message: String

    data class Sender(
        override val subjectType: String,
        override val id: Int,
        override val message: String,
    ) : ChatMessage()

    data class Receiver(
        override val subjectType: String,
        override val id: Int,
        override val message: String,
    ) : ChatMessage()
}
