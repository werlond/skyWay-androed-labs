package com.university.skyway

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun TicketsScreen(onTicketClick: (Ticket) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }

    val filtered = remember(query) {
        if (query.isBlank()) sampleTickets
        else sampleTickets.filter {
            it.from.contains(query, ignoreCase = true) ||
                    it.to.contains(query, ignoreCase = true) ||
                    it.airline.contains(query, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Поиск билетов") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(filtered, key = { it.id }) { ticket ->
                Card(
                    modifier = Modifier.fillMaxWidth().clickable { onTicketClick(ticket) }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${ticket.from} → ${ticket.to}", style = MaterialTheme.typography.titleMedium)
                        Text("${ticket.airline} · ${ticket.date}", style = MaterialTheme.typography.bodyMedium)
                        Text("${ticket.price} ₽", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}