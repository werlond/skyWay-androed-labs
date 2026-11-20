package com.university.skyway

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey

// Вкладки (top-level)
@Serializable data object SearchTab : Route
@Serializable data object FavouritesTab : Route
@Serializable data object AccountTab : Route

// Дочерние экраны
@Serializable data class TicketDetail(val ticketId: Int) : Route
@Serializable data object AccountSettings : Route