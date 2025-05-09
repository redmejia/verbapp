package com.bitinovus.verbapp.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bitinovus.verbapp.presentation.components.bubble.Bubble
import com.bitinovus.verbapp.presentation.components.prompt.PromptTextField
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import com.bitinovus.verbapp.presentation.ui.theme.senderBubble
import com.bitinovus.verbapp.R
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBlack00

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Chat(
    contentPadding: PaddingValues,
) {

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

    var textFieldValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize()
            .background(color = PrimaryWhite00)
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
                            color = if (isSender) PrimaryWhite00 else PrimaryBlack00,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .imePadding(),
            verticalAlignment = Alignment.Bottom
        ) {
            PromptTextField(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(50.dp)),
                value = textFieldValue,
                onValueChange = { textFieldValue = it },
                placeHolder = { Text(text = "Message...") },
            )
            if(textFieldValue.isNotBlank()) {
                IconButton(
                    modifier = Modifier
                        .background(
                            color = PrimaryWhite00,
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(4.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = senderBubble
                    ),
                    onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.filled_send),
                        contentDescription = "send",
                        tint = PrimaryWhite00
                    )
                }
            }
        }
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
