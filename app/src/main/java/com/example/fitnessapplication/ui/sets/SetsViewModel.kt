package com.example.fitnessapplication.ui.sets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SetsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is com.example.fitnessapplication.sets Fragment"
    }
    val text: LiveData<String> = _text
}