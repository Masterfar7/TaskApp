package com.example.tasks.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tasks.data.Task
import com.example.tasks.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(viewModel: TaskViewModel = viewModel()) {

    val list = viewModel.list

    var showAdd by remember { mutableStateOf(false) }
    var showEdit by remember { mutableStateOf(false) }
    var showDetail by remember { mutableStateOf(false) }
    var currentTask by remember { mutableStateOf<Task?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Мои задачи") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->

        Box(Modifier.fillMaxSize().padding(padding)) {

            if (list.isEmpty()) {
                Text(
                    "Нет задач\nНажмите + чтобы добавить",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 88.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(list, key = { it.id }) { task ->
                    TaskCard(
                        task = task,
                        onClick = {
                            currentTask = task
                            showDetail = true
                        },
                        onCheck = { viewModel.toggleTask(task) },
                        onEdit = {
                            currentTask = task
                            showEdit = true
                        },
                        onDelete = { viewModel.deleteTask(task) },
                        modifier = Modifier.animateItemPlacement()
                    )
                }
            }

            FloatingActionButton(
                onClick = { showAdd = true },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 28.dp)
                    .size(60.dp)
            ) {
                Icon(Icons.Default.Add, "Добавить")
            }
        }
    }

    if (showAdd) {
        InputDialog(
            title = "Новая задача",
            name = "",
            desc = "",
            onClose = { showAdd = false },
            onSave = { name, desc ->
                viewModel.addTask(name, desc)
                showAdd = false
            }
        )
    }

    if (showEdit && currentTask != null) {
        val task = currentTask!!

        InputDialog(
            title = "Редактировать",
            name = task.title,
            desc = task.description,
            onClose = {
                showEdit = false
                currentTask = null
            },
            onSave = { name, desc ->
                viewModel.editTask(task, name, desc)
                showEdit = false
                currentTask = null
            }
        )
    }

    if (showDetail && currentTask != null) {
        TaskDetailDialog(
            task = currentTask!!,
            onClose = {
                showDetail = false
                currentTask = null
            }
        )
    }
}