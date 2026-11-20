package com.university.skyway

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.university.skyway.ui.theme.SkyWayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SkyWayTheme {
                SkyWayApp()
            }
        }
    }
}
@Composable
fun SkyWayApp() {
    val topLevelBackStack = remember { TopLevelBackStack(SearchTab) }
    val currentStack = topLevelBackStack.currentStack

    val entryProvider = entryProvider<NavKey> {
        entry<SearchTab> {
            SearchScreen(
                onFindClick = { /* можно перейти на TicketsTab */ topLevelBackStack.addTopLevel(TicketsTab) }
            )
        }
        entry<TicketsTab> {
            TicketsScreen(
                onTicketClick = { ticket ->
                    topLevelBackStack.navigate(TicketDetail(ticket.id))
                }
            )
        }
        entry<AccountTab> {
            AccountScreen(
                onSettingsClick = { topLevelBackStack.navigate(AccountSettings) }
            )
        }
        entry<TicketDetail> { key ->
            TicketDetailScreen(
                ticketId = key.ticketId,
                onBack = { topLevelBackStack.goBack() }
            )
        }
        entry<AccountSettings> {
            AccountSettingsScreen(onBack = { topLevelBackStack.goBack() })
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(
                    SearchTab to "Поиск",
                    TicketsTab to "Билеты",
                    AccountTab to "Аккаунт"
                ).forEach { (tab, label) ->
                    NavigationBarItem(
                        selected = topLevelBackStack.currentTopLevel == tab,
                        onClick = { topLevelBackStack.addTopLevel(tab) },
                        icon = { /* можно иконки */ },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = currentStack,
            modifier = Modifier.padding(padding),
            entryProvider = entryProvider
        )
    }
}