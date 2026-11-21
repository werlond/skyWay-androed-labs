package com.university.skyway

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun FavoritesScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text(stringResource(R.string.tab_favorites), style = MaterialTheme.typography.headlineSmall)
        Text(stringResource(R.string.favorites_placeholder))
    }
}