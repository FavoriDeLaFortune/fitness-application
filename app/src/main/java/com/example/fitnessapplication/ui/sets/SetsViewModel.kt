package com.example.fitnessapplication.ui.sets

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import db.SetDao
import db.SetDataEntity
import db.SetDatabase
import kotlinx.coroutines.launch

open class SetsViewModel(application: Application) : AndroidViewModel(application) {
    val sets: MutableLiveData<SetDataEntity> = MutableLiveData<SetDataEntity>()
    private val context: Context = application

    fun update(name: String, description: String, time: String, calories: String) {
        sets.value = SetDataEntity(0, name, description, time, calories)
        Log.d("viewmodel", "changed")
        val database =
            Room.databaseBuilder(context, SetDatabase::class.java, "set_table")
                .build()
        val databaseDao: SetDao = database.setDao()

        viewModelScope.launch {
            databaseDao.insertAll(sets.value!!)
            Log.d("room", "inserted")
        }
    }

}