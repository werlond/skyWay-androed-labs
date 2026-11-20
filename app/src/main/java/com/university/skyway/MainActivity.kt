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
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
            ScreenContainer {
                SearchScreen(
                    onTicketClick = { ticket ->
                        topLevelBackStack.navigate(TicketDetail(ticket.id))
                    }
                )
            }
        }
        entry<FavouritesTab> {
            ScreenContainer {
                FavoritesScreen()
            }
        }
        entry<AccountTab> {
            ScreenContainer {
                AccountScreen(
                    onSettingsClick = { topLevelBackStack.navigate(AccountSettings) }
                )
            }
        }
        entry<TicketDetail> { key ->
            ScreenContainer {
                TicketDetailScreen(
                    ticketId = key.ticketId,
                    onBack = { topLevelBackStack.goBack() }
                )
            }
        }
        entry<AccountSettings> {
            ScreenContainer {
                AccountSettingsScreen(onBack = { topLevelBackStack.goBack() })
            }
        }
    }

    Scaffold(
        topBar = { SkyWayTopBar() },
        bottomBar = {
            NavigationBar {
                listOf(
                    Triple(SearchTab, Icons.Filled.Search, "Поиск"),
                    Triple(FavouritesTab, Icons.Filled.Favorite, "Избранное"),
                    Triple(AccountTab, Icons.Filled.Person, "Аккаунт")
                ).forEach { (tab, icon, label) ->
                    NavigationBarItem(
                        selected = topLevelBackStack.currentTopLevel == tab,
                        onClick = { topLevelBackStack.addTopLevel(tab) },
                        icon = { Icon(icon, contentDescription = label) },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = currentStack,
            modifier = Modifier.padding(padding),
            transitionSpec = {
                slideInHorizontally { it } togetherWith slideOutHorizontally { -it / 4 }
            },
            popTransitionSpec = {
                slideInHorizontally { -it / 4 } togetherWith slideOutHorizontally { it }
            },
            entryProvider = entryProvider
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkyWayTopBar() {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    )
}

@Composable
fun ScreenContainer(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
        content = content
    )
}