package com.university.skyway

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun TicketDetailScreen(ticketId: Int, onBack: () -> Unit) {
    val ticket = sampleTickets.find { it.id == ticketId }
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        TextButton(onClick = onBack) { Text(stringResource(R.string.common_back)) }
        if (ticket == null) {
            Text(stringResource(R.string.ticket_not_found))
            return@Column
        }
        Text(stringResource(R.string.ticket_number, ticket.id), style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        Text("${ticket.from} → ${ticket.to}", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.ticket_date, ticket.date))
        Text(stringResource(R.string.ticket_airline, ticket.airline))
        Text(stringResource(R.string.ticket_price, ticket.price), style = MaterialTheme.typography.titleMedium)
    }
}