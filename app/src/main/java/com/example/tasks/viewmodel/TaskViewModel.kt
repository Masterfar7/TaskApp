package com.example.tasks.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tasks.data.Task
import com.example.tasks.data.TaskStorage
import kotlinx.coroutines.launch

class TaskViewModel(app: Application) : AndroidViewModel(app) {
    private val storage = TaskStorage(app)
    private var id = 0
    var list = mutableStateListOf<Task>()
        private set

    init {
        viewModelScope.launch {
            val loaded = storage.loadTasks()
            list.addAll(loaded)
            id = (loaded.maxOfOrNull { it.id } ?: 0) + 1
        }
    }

    private fun save() {
        viewModelScope.launch {
            storage.saveTasks(list.toList())
        }
    }

    private fun sort() {
        val completed = list.filter { it.isCompleted }
        val active = list.filter { !it.isCompleted }
        list.clear()
        list.addAll(active + completed)
    }

    fun addTask(name: String, desc: String) {
        list.add(Task(id = id++, title = name, description = desc))
        sort()
        save()
    }

    fun editTask(task: Task, newName: String, newDesc: String) {
        val index = list.indexOfFirst { it.id == task.id }
        if (index != -1) {
            list[index] = list[index].copy(title = newName, description = newDesc)
            save()
        }
    }

    fun deleteTask(task: Task) {
        list.removeAll { it.id == task.id }
        save()
    }

    fun toggleTask(task: Task) {
        val index = list.indexOfFirst { it.id == task.id }
        if (index != -1) {
            list[index] = list[index].copy(isCompleted = !list[index].isCompleted)
            sort()
            save()
        }
    }
}