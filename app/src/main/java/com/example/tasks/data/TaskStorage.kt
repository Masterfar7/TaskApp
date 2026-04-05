package com.example.tasks.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(name = "tasks")

class TaskStorage(private val context: Context) {
    private val gson = Gson()
    private val KEY = stringPreferencesKey("task_list")

    suspend fun loadTasks(): List<Task> {
        val preferences = context.dataStore.data.first()
        val json = preferences[KEY] ?: return emptyList()
        val type = object : TypeToken<List<Task>>() {}.type
        return gson.fromJson(json, type)
    }

    suspend fun saveTasks(list: List<Task>) {
        context.dataStore.edit { preferences ->
            preferences[KEY] = gson.toJson(list)
        }
    }
}