package com.hfad.zodiac4

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel(val dao: TaskDao) : ViewModel() {
    var newTaskName = ""
    //private
    val tasks = dao.getAll()
    private val _navigateToTask = MutableLiveData<Long?>()
    val navigateToTask: LiveData<Long?>
        get() = _navigateToTask

    //needed to format data as string
    //val tasksString = Transformations.map(tasks){
    //  tasks -> formatTasks(tasks)
    //}


    fun addTask(){
        viewModelScope.launch {
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }

    fun onTaskClicked(taskId: Long){
        _navigateToTask.value = taskId
    }

    fun onTaskNavigated(){
        _navigateToTask.value = null
    }

//needed to format data as string
    //display list
    //fun formatTasks(tasks: List<Task>): String {
    //    return tasks.fold("") {
    //        str, item -> str + '\n' + formatTask(item)
    //    }
    //}

    //fun formatTask(task: Task): String{
    //    var str = "ID: ${task.taskId}"
    //    str += '\n' + "Name: ${task.taskName}"
    //    str += '\n' + "Complete: ${task.taskDone}" + '\n'
    //            return str
    //}
}