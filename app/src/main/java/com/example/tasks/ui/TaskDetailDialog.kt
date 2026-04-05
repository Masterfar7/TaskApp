package com.example.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tasks.data.Task

@Composable
fun TaskDetailDialog(task: Task, onClose: () -> Unit) {

    AlertDialog(
        onDismissRequest = onClose,
        title = {
            Text(
                task.title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
            )
        },
        text = {
            Column(Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                if (task.description.isBlank()) {
                    Text(
                        "Нет описания",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Text(task.description, style = MaterialTheme.typography.bodyLarge)
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    if (task.isCompleted) "✓ Выполнено" else "○ Не выполнено",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (task.isCompleted)
                        MaterialTheme.colorScheme.primary
                    else
                        MaterialTheme.colorScheme.outline
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onClose) { Text("Закрыть") }
        }
    )
}