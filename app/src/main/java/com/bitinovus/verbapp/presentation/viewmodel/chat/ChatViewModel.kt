package com.bitinovus.verbapp.presentation.viewmodel.chat

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun chatPrompt(text: String) {
        viewModelScope.launch {
            try {
                if (text.isNotEmpty()) {

                    val chat = _chatUIState.value.toMutableList()

                    val chatUUID = UUID.randomUUID().toString()

                    chat.add(ChatState.Prompt(chatID = chatUUID, text = text))

                    _chatUIState.value = chat

                    val resp  = _chatUIState.value.toMutableList()

                    val generatedText =
                        api.prompt(
                            token = "NOT IMPLEMENTED YET",
                            textPrompt = TextPrompt(text = text)
                        )

                    resp.add(
                        ChatState.Generated(
                            chatID = generatedText.chatID,// this id identify each generated text from backend
                            text = generatedText.text
                        )
                    )
                    delay(2000L)

                    _chatUIState.value = resp
                }

            } catch (e: Exception) {
                Log.e("ERROR", "ERROR: $e")
            }
        }
    }

}