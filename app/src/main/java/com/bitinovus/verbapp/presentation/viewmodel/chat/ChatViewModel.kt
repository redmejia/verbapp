package com.bitinovus.verbapp.presentation.viewmodel.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.verbapp.BuildConfig
import com.bitinovus.verbapp.data.remote.model.TextPromptDto
import com.bitinovus.verbapp.data.remote.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: ApiRepositoryImpl,
) : ViewModel() {

    private val _chatUIState = MutableStateFlow<List<ChatState>>(emptyList())
    val chatUIState: StateFlow<List<ChatState>> = _chatUIState.asStateFlow()

    private val _thinkingState = MutableStateFlow<Boolean>(false)
    val thinkingState: StateFlow<Boolean> = _thinkingState.asStateFlow()

    private val _replyText = MutableStateFlow<ReplyMessageState?>(null)
    val replyTextMessage: StateFlow<ReplyMessageState?> = _replyText.asStateFlow()

    fun setReplyTextMessage(conversationID: String?, message: String?) {
        _replyText.value = ReplyMessageState(
            conversationID = conversationID,
            generatedTextMessage = message
        )
    }

    init {
        viewModelScope.launch {
            val chats = api.getAllChats(token = TOKEN)
            val chatList = chats.flatMap { chatItem ->

                val chatList = mutableListOf<ChatState>()

                if (chatItem.prompt.chatID.isNotBlank()) {
                    chatList.add(
                        ChatState.Prompt(
                            chatID = chatItem.prompt.chatID,
                            userID = chatItem.prompt.userID,
                            conversationID = chatItem.prompt.conversationID,
                            text = chatItem.prompt.text,
                            timestamp = chatItem.prompt.timestamp
                        )
                    )
                }

                if (chatItem.reply.chatID.isNotBlank()) {
                    chatList.add(
                        ChatState.Reply(
                            chatID = chatItem.reply.chatID,
                            userID = chatItem.reply.userID,
                            conversationID = chatItem.reply.conversationID,
                            timestamp = chatItem.reply.timestamp,
                            replyText = chatItem.reply.replyText,
                            text = chatItem.reply.text
                        )
                    )
                }

                if (chatItem.generatedResponse.chatID.isNotBlank()) {
                    chatList.add(
                        ChatState.Generated(
                            chatID = chatItem.generatedResponse.chatID,
                            userID = chatItem.generatedResponse.userID,
                            conversationID = chatItem.generatedResponse.conversationID,
                            text = chatItem.generatedResponse.text,
                            timestamp = chatItem.generatedResponse.timestamp
                        )
                    )
                }

                chatList
            }
            _chatUIState.value = chatList
        }
    }

    fun chatPrompt(text: String) {
        viewModelScope.launch {
            try {
                if (text.isNotEmpty()) {

                    val chat = _chatUIState.value.toMutableList()

                    // val token = "Bearer ${BuildConfig.TOKEN}"
                    val prompt = api.prompt(
                        token = TOKEN,
                        textPromptDto = TextPromptDto(text = text)
                    )

                    chat.add(
                        ChatState.Prompt(
                            chatID = prompt.chatID,
                            conversationID = prompt.conversationID,
                            text = prompt.text
                        )
                    )
                    chat.add(ChatState.Loading(chatID = "Loading-${prompt.chatID}"))

                    _chatUIState.value = chat

                    _thinkingState.value = true
                    val resp = _chatUIState.value
                        .filterNot { it is ChatState.Loading }
                        .toMutableList()

                    val generated = api.aiGeneratedText(
                        token = TOKEN,
                        textPromptDto = TextPromptDto(conversationID = prompt.conversationID)
                    )
                    resp.add(
                        ChatState.Generated(
                            chatID = generated.chatID,// this id identify each generated text from backend
                            userID = generated.chatID,
                            conversationID = generated.conversationID,
                            text = generated.text,
                            timestamp = generated.timestamp
                        )
                    )
                    // delay(2000L) testing
                    _thinkingState.value = false
                    _chatUIState.value = resp
                }
            } catch (_: Exception) {
            }
        }
    }

    // Reply to generated text
    fun replyToGeneratedTextMessage(conversationID: String, prompt: String, textToReply: String) {
        viewModelScope.launch {
            try {

                val chat = _chatUIState.value.toMutableList()


                val reply =
                    api.replyToGeneratedText(
                        token = TOKEN,
                        textPromptDto = TextPromptDto(
                            conversationID = conversationID,
                            text = prompt
                        )
                    )


                chat.add(
                    ChatState.Reply(
                        chatID = reply.chatID,
                        replyText = textToReply,
                        text = prompt
                    )
                )
                chat.add(ChatState.Loading(chatID = "Reply_Loading-${reply.chatID}"))

                _chatUIState.value = chat

                _thinkingState.value = true

                val responseToReply = _chatUIState.value
                    .filterNot { it is ChatState.Loading }
                    .toMutableList()

                val generatedText =
                    api.aiResponseToReply(
                        token = TOKEN,
                        conversationID = reply.conversationID
                    )


                responseToReply.add(
                    ChatState.Generated(
                        chatID = generatedText.chatID,
                        conversationID = generatedText.conversationID,
                        text = generatedText.text
                    )
                )

                _thinkingState.value = false
                _chatUIState.value = responseToReply

            } catch (_: Exception) {
            }
        }
    }

    companion object {
        private const val TOKEN = "Bearer ${BuildConfig.TOKEN}"
    }

}