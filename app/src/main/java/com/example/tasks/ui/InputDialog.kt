package com.example.tasks.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputDialog(
    title: String,
    name: String,
    desc: String,
    onClose: () -> Unit,
    onSave: (String, String) -> Unit
) {

    var inputName by remember { mutableStateOf(name) }
    var inputDesc by remember { mutableStateOf(desc) }

    AlertDialog(
        onDismissRequest = onClose,
        title = { Text(title) },
        text = {

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                OutlinedTextField(
                    value = inputName,
                    onValueChange = { inputName = it },
                    label = { Text("Название") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = inputDesc,
                    onValueChange = { inputDesc = it },
                    label = { Text("Описание") },
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(inputName.trim(), inputDesc.trim())
                },
                enabled = inputName.isNotBlank()
            ) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            TextButton(onClick = onClose) { Text("Отмена") }
        }
    )
}