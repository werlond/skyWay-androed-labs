package com.university.skyway

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.university.skyway.ui.theme.SkyWayTheme
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val notImplementedMessage = stringResource(R.string.notice_not_implemented)
    val placeholderNotifier = remember(notImplementedMessage, snackbarHostState) {
        PlaceholderNotifier {
            scope.showNotImplemented(snackbarHostState, notImplementedMessage)
        }
    }

    CompositionLocalProvider(LocalPlaceholderNotifier provides placeholderNotifier) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                SearchScreen(
                    onFindClick = MaterialTheme.snackbar::notifyPlaceholder
                )
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    onFindClick: () -> Unit = {}
) {
    var from by rememberSaveable { mutableStateOf("") }
    var to by rememberSaveable { mutableStateOf("") }
    var dateText by rememberSaveable { mutableStateOf("") }
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var selectedClass by rememberSaveable { mutableIntStateOf(0) }
    var directOnly by rememberSaveable { mutableStateOf(false) }

    var returnTicket by rememberSaveable { mutableStateOf(false) }
    var returnDateText by rememberSaveable { mutableStateOf("") }
    var showReturnDatePicker by rememberSaveable { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val returnDatePickerState = rememberDatePickerState()

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd.MM.yyyy") }

    val classes = listOf(
        stringResource(R.string.class_economy),
        stringResource(R.string.class_comfort),
        stringResource(R.string.class_business)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
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
            Spacer(Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.screen_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = from,
                onValueChange = { from = it },
                label = { Text(stringResource(R.string.label_from)) },
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = {
                val temp = from
                from = to
                to = temp
            }) {
                Icon(
                    imageVector = Icons.Filled.SwapHoriz,
                    contentDescription = stringResource(R.string.btn_swap)
                )
            }
            OutlinedTextField(
                value = to,
                onValueChange = { to = it },
                label = { Text(stringResource(R.string.label_to)) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(16.dp))

        FilledTonalButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                if (dateText.isEmpty()) stringResource(R.string.btn_pick_date)
                else dateText
            )
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.label_return_ticket),
                modifier = Modifier.weight(1f)
            )
            Switch(
                checked = returnTicket,
                onCheckedChange = { returnTicket = it }
            )
        }

        AnimatedVisibility(visible = returnTicket) {
            Column {
                Spacer(Modifier.height(8.dp))
                FilledTonalButton(
                    onClick = { showReturnDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (returnDateText.isEmpty())
                            stringResource(R.string.btn_pick_return_date)
                        else returnDateText
                    )
                }
            }
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            classes.forEachIndexed { index, label ->
                SegmentedButton(
                    selected = selectedClass == index,
                    onClick = { selectedClass = index },
                    shape = SegmentedButtonDefaults.itemShape(index, classes.size)
                ) {
                    Text(label)
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                stringResource(R.string.label_direct_only),
                modifier = Modifier.weight(1f)
            )
            Switch(checked = directOnly, onCheckedChange = { directOnly = it })
        }

        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

        Button(
            onClick = onFindClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.btn_find))
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        dateText = Instant.ofEpochMilli(it)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .format(dateFormatter)
                    }
                    showDatePicker = false
                }) {
                    Text(stringResource(R.string.btn_ok))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showReturnDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showReturnDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    returnDatePickerState.selectedDateMillis?.let {
                        returnDateText = Instant.ofEpochMilli(it)
                            .atZone(ZoneOffset.UTC)
                            .toLocalDate()
                            .format(dateFormatter)
                    }
                    showReturnDatePicker = false
                }) {
                    Text(stringResource(R.string.btn_ok))
                }
            }
        ) {
            DatePicker(state = returnDatePickerState)
        }
    }
}

@Preview(showBackground = true, name = "Светлая тема")
@Composable
fun SearchScreenPreviewLight() {
    SkyWayTheme(darkTheme = false, dynamicColor = false) {
        CompositionLocalProvider(
            LocalPlaceholderNotifier provides PlaceholderNotifier {}
        ) {
            SkyWayApp()
        }
    }
}

@Preview(showBackground = true, name = "Тёмная тема")
@Composable
fun SearchScreenPreviewDark() {
    SkyWayTheme(darkTheme = true, dynamicColor = false) {
        CompositionLocalProvider(
            LocalPlaceholderNotifier provides PlaceholderNotifier {}
        ) {
            SkyWayApp()
        }
    }
}