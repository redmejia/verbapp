package com.bitinovus.verbapp.presentation.viewmodel.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bitinovus.verbapp.BuildConfig
import com.bitinovus.verbapp.data.remote.model.TextPrompt
import com.bitinovus.verbapp.data.remote.repository.ApiRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val api: ApiRepositoryImpl,
) : ViewModel() {

    private val _chatUIState = MutableStateFlow<List<ChatState>>(emptyList())
    val chatUIState: StateFlow<List<ChatState>> = _chatUIState.asStateFlow()

    private val _thinkingState = MutableStateFlow<Boolean>(false)
    val thinkingState: StateFlow<Boolean> = _thinkingState.asStateFlow()

    private val _replyMessage = MutableStateFlow<String?>(null)
    val replyMessage: StateFlow<String?> = _replyMessage.asStateFlow()

    fun setReplyMessage(message: String?) {
        _replyMessage.value = message
    }


    fun chatPrompt(text: String) {
        viewModelScope.launch {
            try {
                if (text.isNotEmpty()) {

                    val chat = _chatUIState.value.toMutableList()

                    val chatUUID = UUID.randomUUID().toString()

                    chat.add(ChatState.Prompt(chatID = chatUUID, text = text))

                    chat.add(ChatState.Loading(chatID = "Loading-$chatUUID"))

                    _chatUIState.value = chat

                    _thinkingState.value = true
                    val resp = _chatUIState.value
                        .filterNot { it is ChatState.Loading }
                        .toMutableList()

                    val token = "Bearer ${BuildConfig.TOKEN}"
                    val generatedText =
                        api.prompt(
                            token = token,
                            textPrompt = TextPrompt(text = text)
                        )

                    resp.add(
                        ChatState.Generated(
                            chatID = generatedText.chatID,// this id identify each generated text from backend
                            text = generatedText.text
                        )
                    )
                    // delay(2000L) testing
                    _thinkingState.value = false
                    _chatUIState.value = resp
                }

            } catch (e: Exception) {
                Log.e("ERROR", "ERROR: $e")
            }
        }
    }

    fun replyMessage(prompt: String, messageToReply: String) {
        viewModelScope.launch {
            try {
                if (messageToReply.isNotBlank()) {
                    val chat = _chatUIState.value.toMutableList()

                    val chatUUID = UUID.randomUUID().toString()
                    chat.add(
                        ChatState.Reply(
                            chatID = chatUUID,
                            generatedText = messageToReply,
                            text = prompt
                        )
                    )
                    chat.add(ChatState.Loading(chatID = "Reply_Loading-$chatUUID"))

                    _chatUIState.value = chat

                    _thinkingState.value = true

                   val responseToReply = _chatUIState.value
                        .filterNot { it is ChatState.Loading }
                        .toMutableList()

                    val token = "Bearer ${BuildConfig.TOKEN}"
                    val generatedText =
                        api.prompt(
                            token = token,
                            textPrompt = TextPrompt(text = prompt) // this will get an object
                        )

                    responseToReply.add(
                        ChatState.Generated(
                            chatID = generatedText.chatID,
                            text = generatedText.text
                        )
                    )

                    _thinkingState.value = false
                    _chatUIState.value = responseToReply

                }
            } catch (_: Exception) {
            }
        }
    }

}