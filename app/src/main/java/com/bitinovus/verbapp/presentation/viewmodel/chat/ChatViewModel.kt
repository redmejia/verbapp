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

    private val _replyText = MutableStateFlow<String?>(null)
    val replyTextMessage: StateFlow<String?> = _replyText.asStateFlow()

    fun setReplyTextMessage(message: String?) {
        _replyText.value = message
    }


    fun chatPrompt(text: String) {
        viewModelScope.launch {
            try {
                if (text.isNotEmpty()) {

                    val chat = _chatUIState.value.toMutableList()

                    val token = "Bearer ${BuildConfig.TOKEN}"
                    val prompt = api.prompt(
                        token = token,
                        textPromptDto = TextPromptDto(text = text)
                    )
                    Log.d("CHAT", "chatPrompt RESP >>>>>>: $prompt")
                    // val chatUUID = UUID.randomUUID().toString()

                    chat.add(ChatState.Prompt(chatID = prompt.chatID, text = prompt.text))

                     chat.add(ChatState.Loading(chatID = "Loading-${prompt.chatID}"))

                    _chatUIState.value = chat

//                    _thinkingState.value = true
//                    val resp = _chatUIState.value
//                        .filterNot { it is ChatState.Loading }
//                        .toMutableList()
//
//                    val token = "Bearer ${BuildConfig.TOKEN}"
//                    val generatedText =
//                        api.prompt(
//                            token = token,
//                            textPrompt = TextPrompt(text = text)
//                        )
//
//                    resp.add(
//                        ChatState.Generated(
//                            chatID = generatedText.chatID,// this id identify each generated text from backend
//                            text = generatedText.text
//                        )
//                    )
//                    // delay(2000L) testing
//                    _thinkingState.value = false
//                    _chatUIState.value = resp
                }

            } catch (e: Exception) {
                Log.e("ERROR", "ERROR: $e")
            }
        }
    }

    // Reply to generated text
    fun replyToGeneratedTextMessage(prompt: String, textToReply: String) {
        viewModelScope.launch {
            try {
                if (textToReply.isNotBlank()) {
                    val chat = _chatUIState.value.toMutableList()

                    val chatUUID = UUID.randomUUID().toString()
                    chat.add(
                        ChatState.Reply(
                            chatID = chatUUID,
                            replyText = textToReply,
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
                            textPromptDto = TextPromptDto(text = prompt) // this will get an object
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