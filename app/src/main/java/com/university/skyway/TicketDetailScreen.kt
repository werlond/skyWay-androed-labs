package com.university.skyway

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TicketDetailScreen(ticketId: Int, onBack: () -> Unit) {
    val ticket = sampleTickets.find { it.id == ticketId }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text("Назад") }
        if (ticket == null) {
            Text("Билет не найден")
            return@Column
        }
        Text("Билет #${ticket.id}", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("${ticket.from} → ${ticket.to}", style = MaterialTheme.typography.titleLarge)
        Text("Дата: ${ticket.date}")
        Text("Авиакомпания: ${ticket.airline}")
        Text("Цена: ${ticket.price} ₽", style = MaterialTheme.typography.titleMedium)
    }
}