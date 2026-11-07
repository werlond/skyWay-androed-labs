package com.university.skyway

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun interface PlaceholderNotifier {
    fun notifyPlaceholder()
}

val LocalPlaceholderNotifier = staticCompositionLocalOf<PlaceholderNotifier> {
    error("PlaceholderNotifier не предоставлен")
}

fun CoroutineScope.showNotImplemented(
    snackbarHostState: SnackbarHostState,
    message: String
) {
    launch {
        snackbarHostState.currentSnackbarData?.dismiss()
        snackbarHostState.showSnackbar(
            message = message,
            withDismissAction = true,
            duration = SnackbarDuration.Short
        )
    }
}

val MaterialTheme.snackbar: PlaceholderNotifier
    @Composable
    @ReadOnlyComposable
    get() = LocalPlaceholderNotifier.current