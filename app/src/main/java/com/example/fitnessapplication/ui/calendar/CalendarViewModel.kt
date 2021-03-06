package com.example.fitnessapplication.ui.calendar

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import db.*
import kotlinx.coroutines.launch

class CalendarViewModel(application: Application) : AndroidViewModel(application) {
    private val sets : MutableLiveData<CalendarSetDataEntity> = MutableLiveData()


    private val context: Context = application
    val database =
        Room.databaseBuilder(context, CalendarSetDatabase::class.java, "calendar_set_table")
            .build()
    val databaseDao: CalendarSetDao = database.calendarSetDao()

    fun insert(date: String, name: String, time: String) {
        viewModelScope.launch {
            databaseDao.insertAll(CalendarSetDataEntity(0, date, name, time))
        }
    }

    suspend fun get(): List<CalendarSetDataEntity> {
        return databaseDao.getAll()
    }

    suspend fun getByDate(date: String): List<CalendarSetDataEntity> {
        return databaseDao.getAllByDate(date)
    }
}