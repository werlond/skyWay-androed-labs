package com.university.skyway

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun AccountScreen(onSettingsClick: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Аккаунт", style = MaterialTheme.typography.headlineSmall)
        Text("Здесь отображаются купленные билеты (заглушка)")
        Spacer(Modifier.height(16.dp))
        Button(onClick = onSettingsClick) { Text("Настройки") }
    }
}

@Composable
fun AccountSettingsScreen(onBack: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("Назад") }
        Text("Настройки аккаунта")
    }
}