package com.example.smart_food_planner.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smart_food_planner.data.repository.Gemini_Repository
import com.example.smart_food_planner.model.dataClasses.ChatMessage
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val repository = Gemini_Repository()

    private val _botReply = MutableLiveData<String>()
    val botReply: LiveData<String> get() = _botReply


    fun sendRequest( userMessage: String) = viewModelScope.launch  {

        repository.sendRequest(userMessage,{ reply ->

            _botReply.postValue(reply)

        })
    }


  }