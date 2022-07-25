package com.hfad.zodiac4

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


//Used to add TasksViewModel to TasksFragment

class TasksViewModelFactory(private val dao: TaskDao) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TasksViewModel::class.java)){
            return TasksViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}