package com.bitinovus.verbapp.presentation.screens.chat


import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bitinovus.verbapp.presentation.components.bubble.Bubble
import com.bitinovus.verbapp.presentation.components.prompt.PromptTextField
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryGray00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryPurple40
import com.bitinovus.verbapp.R
import com.bitinovus.verbapp.presentation.components.animations.pulse.PulseDimension
import com.bitinovus.verbapp.presentation.components.bubble.ActionBubble
import com.bitinovus.verbapp.presentation.components.bubble.CopyActionButton
import com.bitinovus.verbapp.presentation.components.loader.PulseLoader
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryWhite00
import com.bitinovus.verbapp.presentation.ui.theme.PrimaryBlack00
import com.bitinovus.verbapp.presentation.viewmodel.chat.ChatState
import com.bitinovus.verbapp.presentation.viewmodel.chat.ChatViewModel
import android.content.ClipData
import android.content.Context
import android.content.ClipboardManager
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import com.bitinovus.verbapp.presentation.components.bubble.ActionButton
import com.bitinovus.verbapp.presentation.components.bubble.ReplyBubble

@Composable
fun Chat(
    modifier: Modifier = Modifier,
    chatViewModel: ChatViewModel = hiltViewModel(),
) {
    val messageList by chatViewModel.chatUIState.collectAsState()
    val thinking by chatViewModel.thinkingState.collectAsState()
    val replyTextMessage by chatViewModel.replyTextMessage.collectAsState()

    var textPrompt by remember { mutableStateOf("") }

    val lazyListState = rememberLazyListState()

    val keyboardController = LocalSoftwareKeyboardController.current


    // Auto scroll when new message add to list
    LaunchedEffect(messageList.size) {
        // Scroll to the first item, scroll to bottom
        if (messageList.isNotEmpty()) {
            lazyListState.animateScrollToItem(0)
        }
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            // .statusBarsPadding()
            // .navigationBarsPadding()
            .background(color = PrimaryWhite00)
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            state = lazyListState,
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(top = 10.dp, bottom = 10.dp),
            reverseLayout = true,
        ) {
            items(items = messageList.reversed(), key = { it.chatID }) { chat ->
                when (chat) {
                    is ChatState.Prompt ->
                        Bubble(
                            backgroundColor = PrimaryPurple40,
                            isSender = true,
                            contentText = {
                                Text(
                                    text = chat.text,
                                    color = PrimaryWhite00,
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        )

                    is ChatState.Reply -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            ReplyBubble(
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .wrapContentWidth(Alignment.End)
                                    .background(
                                        color = PrimaryGray00,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                contentAlignment = Alignment.CenterEnd
                            ) {
                                Text(
                                    chat.replyText,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis,
                                    color = PrimaryBlack00,
                                    modifier = Modifier
                                        .padding(4.dp),
                                )
                            }
                            Bubble(
                                backgroundColor = PrimaryPurple40,
                                isSender = true,
                                contentText = {
                                    Text(
                                        text = chat.text,
                                        color = PrimaryWhite00,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                },
                            )
                        }
                    }

                    is ChatState.Generated -> {

                        var visible by rememberSaveable(chat.chatID) { mutableStateOf(false) }

                        Column(
                            modifier = Modifier.animateItem(
                                fadeInSpec = spring(stiffness = Spring.StiffnessVeryLow),
                                fadeOutSpec = spring(stiffness = Spring.StiffnessVeryLow),
                                placementSpec = null
                            )
                        ) {
                            Bubble(
                                modifier = Modifier
                                    .clickable(
                                        indication = null,
                                        interactionSource = null
                                    ){
                                        visible = !visible
                                    },
                                backgroundColor = PrimaryGray00,
                                isSender = false,
                                contentText = {
                                    Text(
                                        text = chat.text,
                                        color = PrimaryBlack00,
                                        modifier = Modifier.padding(4.dp)
                                    )
                                }
                            )
                            AnimatedVisibility(
                                visible = visible,
                                enter =
                                    slideInVertically(
                                        initialOffsetY = { it }
                                    ) +
                                            expandVertically(expandFrom = Alignment.Top) +
                                            scaleIn(transformOrigin = TransformOrigin(0.5f, 0f)) +
                                            fadeIn(initialAlpha = 0.3f),
                                exit = slideOutVertically(
                                    targetOffsetY = { it }
                                ) + shrinkVertically() + fadeOut()
                            ) {
                                ActionBubble(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                ) {
                                    CopyActionButton(
                                        unselectedImage = R.drawable.content_copy,
                                        selectedImage = R.drawable.filled_content_copy,
                                        contentDescription = "copy action",
                                        onClick = {
                                            val clipboardManager: ClipboardManager =
                                                context.getSystemService(Context.CLIPBOARD_SERVICE)
                                                        as ClipboardManager
                                            val clip: ClipData =
                                                ClipData.newPlainText("", chat.text)
                                            clipboardManager.setPrimaryClip(clip)
                                        }
                                    )
                                    // Reply action
                                    ActionButton(
                                        selectedImage = R.drawable.rounded_reply,
                                        contentDescription = "reply prompt",
                                        onClick = {
                                            chatViewModel.setReplyTextMessage(chat.text)
                                            keyboardController?.show()
                                        }
                                    )
                                    // Add more action buttons here Save, Share... etc
                                    // SaveActionButton(
                                    //    selectedImage = R.drawable.data_saver,
                                    //    contentDescription = "save action",
                                    //    onClick = { Log.d("ACTION", "Chat: SAVE") }
                                    // )
                                }
                            }
                        }
                    }

                    is ChatState.Loading -> {
                        PulseLoader(
                            pulsarRadius = 10f,
                            dimension = PulseDimension(
                                outerDimensionSize = Pair(10, 10),
                                innerDimensionSize = Pair(15, 15)
                            ),
                            isLoading = thinking
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .imePadding(),
        ) {
            if (replyTextMessage != null) {
                ReplyBubble(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .background(
                            color = PrimaryWhite00,
                            shape = RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp)
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 2.dp, vertical = 2.dp)
                    ) {
                        Text(
                            replyTextMessage!!,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = PrimaryBlack00,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Icon(
                            Icons.Filled.Clear,
                            contentDescription = "clear reply bubble",
                            modifier = Modifier.clickable {
                                chatViewModel.setReplyTextMessage(
                                    null
                                )
                            }
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    // .padding(vertical = 4.dp)
                    .imePadding(),
                verticalAlignment = Alignment.Bottom
            ) {
                PromptTextField(
                    modifier = Modifier
                        .weight(2f)
                        .clip(RoundedCornerShape(30.dp)),
                    value = textPrompt,
                    onValueChange = { textPrompt = it },
                    placeHolder = { Text(text = "Message...") },
                )
                if (textPrompt.isNotBlank()) {
                    IconButton(
                        modifier = Modifier
                            .padding(4.dp),
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = PrimaryPurple40
                        ),
                        onClick = {
                            if (replyTextMessage != null) {
                                chatViewModel.replyToGeneratedTextMessage(
                                    prompt = textPrompt,
                                    textToReply = replyTextMessage!!
                                )
                                chatViewModel.setReplyTextMessage(null)
                            } else {
                                chatViewModel.chatPrompt(text = textPrompt)
                            }
                            textPrompt = ""

                        }) {
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
}

