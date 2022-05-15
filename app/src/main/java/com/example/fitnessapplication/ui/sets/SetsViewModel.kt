package com.example.fitnessapplication.ui.sets

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class SetsViewModel : ViewModel() {
    val nameData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val descriptionData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val timeData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val caloriesData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}