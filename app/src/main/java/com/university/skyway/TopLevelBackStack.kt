package com.university.skyway

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.navigation3.runtime.NavKey
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class TopLevelBackStack(startKey: NavKey) {
    private val backStacks = mutableMapOf<NavKey, SnapshotStateList<NavKey>>()

    init {
        listOf(SearchTab, FavouritesTab, AccountTab).forEach { tab ->
            backStacks[tab] = mutableStateListOf(tab)
        }
    }

    var currentTopLevel: NavKey by mutableStateOf(startKey)
        private set

    val currentStack: SnapshotStateList<NavKey>
        get() = backStacks.getValue(currentTopLevel)

    fun addTopLevel(key: NavKey) {
        if (key in backStacks) {
            currentTopLevel = key
        }
    }

    fun navigate(route: NavKey) {
        if (route in backStacks.keys) {
            currentTopLevel = route
        } else {
            currentStack.add(route)
        }
    }

    fun goBack(): Boolean {
        return if (currentStack.size > 1) {
            currentStack.removeAt(currentStack.lastIndex)
            true
        } else false
    }
}