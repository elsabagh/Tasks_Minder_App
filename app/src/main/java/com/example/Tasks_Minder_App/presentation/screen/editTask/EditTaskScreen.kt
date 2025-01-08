package com.example.Tasks_Minder_App.presentation.screen.editTask

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.data.model.Task
import com.example.Tasks_Minder_App.presentation.common.components.ActionToolbar
import com.example.Tasks_Minder_App.presentation.ui.theme.TaskMinderTheme
import java.util.Calendar

/**
 * Composable for the Edit Task screen.
 *
 * @param navigateBack A lambda triggered when the user navigates back from this screen.
 * @param onTaskSaved A lambda triggered when the task is successfully saved.
 * @param modifier A [Modifier] for custom styling or layout adjustments.
 */
@Composable
fun EditTaskScreen(
    navigateBack: () -> Unit,
    onTaskSaved: () -> Unit,
    modifier: Modifier = Modifier
) {
    val editTaskViewModel: EditTaskViewModel = hiltViewModel()
    val task by editTaskViewModel.task.collectAsStateWithLifecycle()
    val isAlertEnabled by editTaskViewModel.isAlertEnabled.collectAsStateWithLifecycle()
    val isTaskSaved by editTaskViewModel.isTaskSaved.collectAsStateWithLifecycle()

    // Use remember to memoize lambdas
//    Use remember for stable lambdas: Lambdas that perform actions like changing dates, times, priorities, or saving data—actions
//    that don’t need to respond to each individual user input—can be safely memoized with remember to optimize performance.

//    Avoid remember for text input handlers: Lambdas that handle user input in real-time (like typing into text fields) should not be memoized,
//    as they need to be recomposed frequently to reflect the latest state in the UI.

    val onDateChangeMemoized: (Long) -> Unit = remember { editTaskViewModel::onDateChange }
    val onTimeChangeMemoized: (Int, Int) -> Unit = remember { editTaskViewModel::onTimeChange }
    val onPriorityChangeMemoized: (String) -> Unit =
        remember { editTaskViewModel::onPriorityChange }
    val onAlertChangeMemoized: (Boolean) -> Unit = remember { editTaskViewModel::onAlertChange }
    val onSaveTaskMemoized: () -> Unit = remember { editTaskViewModel::onSaveTask }

    LaunchedEffect(isTaskSaved) {
        if (isTaskSaved) {
            onTaskSaved()
            editTaskViewModel.resetTaskSaved()
        }
    }

    LaunchedEffect(editTaskViewModel) {
        editTaskViewModel.refreshAlertEnabled()
    }

    EditTaskScreenContent(
        isEditing = task.id.isNotBlank(),
        task = task,
        onNavigateBack = navigateBack,
        onDateChange = onDateChangeMemoized,
        onTimeChange = onTimeChangeMemoized,
        onTitleChange = editTaskViewModel::onTitleChange,
        onDescriptionChange = editTaskViewModel::onDescriptionChange,
        onPriorityChange = onPriorityChangeMemoized,
        isAlertEnabled = isAlertEnabled,
        onAlertChange = onAlertChangeMemoized,
        onSaveTask = onSaveTaskMemoized,
        modifier = modifier
    )
}

/**
 * The core UI for editing task details.
 *
 * @param isEditing Indicates if the screen is in editing mode.
 * @param task The [Task] object containing current task details.
 * @param onNavigateBack A lambda triggered when the user navigates back.
 * @param onDateChange Lambda to handle date changes in the task.
 * @param onTimeChange Lambda to handle time changes in the task.
 * @param onTitleChange Lambda to handle changes in the task title.
 * @param onDescriptionChange Lambda to handle changes in the task description.
 * @param onPriorityChange Lambda to handle priority changes in the task.
 * @param isAlertEnabled Whether the alert toggle is enabled for the task.
 * @param onAlertChange Lambda to handle alert enable/disable changes.
 * @param onSaveTask Lambda to handle task saving action.
 * @param modifier A [Modifier] for custom styling or layout adjustments.
 */
@Composable
fun EditTaskScreenContent(
    isEditing: Boolean,
    task: Task,
    onNavigateBack: () -> Unit,
    onDateChange: (Long) -> Unit,
    onTimeChange: (Int, Int) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriorityChange: (String) -> Unit,
    isAlertEnabled: Boolean,
    onAlertChange: (Boolean) -> Unit,
    onSaveTask: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    var isSaveEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(task) {
        isSaveEnabled = task.dueDate.isNotBlank() &&
                task.title.isNotBlank() &&
                task.description.isNotBlank()
    }

    Scaffold(
        topBar = {
            ActionToolbar(
                title = if (isEditing) {
                    R.string.edit_task
                } else {
                    R.string.create_task
                },
                onNavigateUp = onNavigateBack,
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.edit_task_screen_vertical_spacing)),
            modifier = modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.screen_padding))
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = task.dueDate,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.task_date_placeholder),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { showDatePickerDialog = !showDatePickerDialog }) {
                        Icon(
                            painter = painterResource(R.drawable.date_range_24),
                            contentDescription = stringResource(R.string.select_date_content_description),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            TaskTitleAndDescription(
                title = task.title,
                onTitleChange = onTitleChange,
                description = task.description,
                onDescriptionChange = onDescriptionChange

            )

            OutlinedTextField(
                value = task.dueTime,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(
                        text = stringResource(R.string.task_time_placeholder),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = { showTimePickerDialog = !showTimePickerDialog }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.access_time_24),
                            contentDescription = stringResource(R.string.select_time_content_description),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            TaskPriority(
                selectPriority = task.priority,
                onPriorityChange = onPriorityChange,
            )

            if (isAlertEnabled) {
                AlertOption(
                    checked = task.alert,
                    onCheckedChange = onAlertChange
                )
            }

            Button(
                shape = MaterialTheme.shapes.medium,
                onClick = onSaveTask,
                enabled = isSaveEnabled,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Save",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            if (showDatePickerDialog) {
                DateSelectionDialog(
                    onDismissRequest = {
                        showDatePickerDialog = false
                    },
                    onConfirmClicked = { currentDateInMillis ->
                        onDateChange(currentDateInMillis)
                        showDatePickerDialog = false
                    },
                    onDismissButtonClicked = {
                        showDatePickerDialog = false
                    }
                )
            }

            if (showTimePickerDialog) {
                TimeSelectionDialog(
                    onDismiss = {
                        showTimePickerDialog = false
                    },
                    onConfirm = { hour, minute ->
                        onTimeChange(hour, minute)
                        showTimePickerDialog = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelectionDialog(
    onDismissRequest: () -> Unit,
    onConfirmClicked: (Long) -> Unit,
    onDismissButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val datePickerState = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    onConfirmClicked(datePickerState.selectedDateMillis!!)
                },
                enabled = datePickerState.selectedDateMillis != null
            ) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            Button(onClick = onDismissButtonClicked) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        modifier = modifier
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}


/**
 * A dialog for selecting a time using a time picker.
 *
 * @param onConfirm Lambda triggered when the user confirms the selected time, providing the hour and minute.
 * @param onDismiss Lambda triggered when the user dismisses or cancels the dialog.
 * @param modifier A [Modifier] for custom styling or layout adjustments.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelectionDialog(
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false
    )
    var isAM by remember { mutableStateOf(currentTime.get(Calendar.AM_PM) == Calendar.AM) }

    TimePickerDialog(
        onDismiss = onDismiss,
        onConfirm = {
            val hour = if (isAM) timePickerState.hour else timePickerState.hour + 12
            onConfirm(hour, timePickerState.minute)
        },
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TimePicker(
                state = timePickerState
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { isAM = true }) {
                    Text(
                        text = "AM",
                        color = if (isAM) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
                TextButton(onClick = { isAM = false }) {
                    Text(
                        text = "PM",
                        color = if (isAM) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

/**
 * A reusable dialog for displaying time picker or other custom content.
 *
 * @param onDismiss Lambda triggered when the dialog is dismissed.
 * @param onConfirm Lambda triggered when the user confirms the dialog action.
 * @param modifier A [Modifier] for custom styling or layout adjustments.
 * @param content The composable content to display within the dialog.
 */
@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val dismissText = stringResource(R.string.cancel)
    val confirmText = stringResource(R.string.confirm)
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText)
            }
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        text = { content() },
        modifier = modifier
    )
}

@Composable
fun TaskTitleAndDescription(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.task_title_and_description_vertical_spacing)),
        modifier = modifier
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChange,
            maxLines = 2,
            placeholder = {
                Text(
                    text = stringResource(R.string.task_title),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            minLines = 3,
            placeholder = {
                Text(
                    text = stringResource(R.string.task_description),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun TaskPriority(
    selectPriority: String,
    onPriorityChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.task_priority_vertical_spacing))
    ) {
        Text(
            text = stringResource(R.string.task_priority),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.task_priority_horizontal_spacing))
        ) {
            val priorities = listOf(
                stringResource(R.string.low),
                stringResource(R.string.medium),
                stringResource(R.string.high)
            )
            priorities.forEach { priority ->
                PriorityChip(
                    text = priority,
                    selected = priority == selectPriority,
                    onSelected = { onPriorityChange(priority) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun PriorityChip(
    text: String,
    selected: Boolean,
    onSelected: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        onClick = onSelected,
        label = {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        selected = selected,
        leadingIcon = {
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(R.string.selected_chip_icon_content_description)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun AlertOption(
    checked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.get_alert_for_for_this_task))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

@Preview
@Composable
private fun AlertOptionPreview() {
    TaskMinderTheme {
        AlertOption(
            onCheckedChange = {}
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    showSystemUi = true,
)
@Composable
private fun EditTaskScreenPreview() {
    TaskMinderTheme {
        EditTaskScreen(
            navigateBack = {},
            onTaskSaved = {}
        )
    }
}

@Preview
@Composable
private fun TimeSelectionDialogPreview() {
    TaskMinderTheme {
        TimeSelectionDialog(
            onConfirm = { _, _ -> },
            onDismiss = {}
        )
    }
}

@Preview
@Composable
private fun TimePickerDialogPreview() {
    TaskMinderTheme {
        TimePickerDialog(
            onDismiss = {},
            onConfirm = {}
        ) {}
    }
}