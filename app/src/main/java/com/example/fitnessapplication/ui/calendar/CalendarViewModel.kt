package com.example.fitnessapplication.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import db.SetDataEntity

class CalendarViewModel : ViewModel() {
    private val chosen : MutableLiveData<SetDataEntity> = MutableLiveData()

    fun update(set: SetDataEntity) {
        chosen.value = set
    }
}