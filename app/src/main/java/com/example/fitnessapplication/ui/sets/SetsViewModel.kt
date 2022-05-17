package com.example.fitnessapplication.ui.sets

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import db.Set

open class SetsViewModel : ViewModel() {
    val sets: MutableLiveData<Set> = MutableLiveData<Set>()

    fun update(name: String, description: String, time: String, calories: String) {
        sets.value = Set(name, description, time, calories)
        Log.d("viewmodel", "changed")
    }

}