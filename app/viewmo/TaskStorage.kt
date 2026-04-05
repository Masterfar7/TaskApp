package com.example.tasklist.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TaskStorage(context: Context) {
    private val prefs = context.getSharedPreferences("tasks", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadTasks(): List<Task> {
        val json = prefs.getString("list", null) ?: return emptyList()
        val type = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveTasks(list: List<Task>) {
        prefs.edit().putString("list", gson.toJson(list)).apply()
    }
}