package com.diiage.template.ui.core.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties

/**
 * Dialog component for displaying error messages to the user.
 *
 * @param message The error message to display
 * @param onDismiss Callback when the dialog is dismissed
 * @param onRetry Optional callback for retry action
 */
@Composable
fun ErrorDialog(
    message: String,
    onDismiss: () -> Unit,
    onRetry: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Error") },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(text = "OK")
            }
        },
        dismissButton = onRetry?.let {
            {
                Button(onClick = {
                    onRetry()
                    onDismiss()
                }) {
                    Text(text = "Retry")
                }
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    )
}