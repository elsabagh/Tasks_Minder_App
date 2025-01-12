package com.example.Tasks_Minder_App.presentation.screen.task

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.data.model.Task
import com.example.Tasks_Minder_App.presentation.common.snackbar.SnackBarManager
import com.example.Tasks_Minder_App.presentation.ui.theme.TaskMinderTheme

/**
 * A Composable screen that displays the list of tasks along with various controls such as:
 * - A toolbar for navigating to settings.
 * - A floating action button to add new tasks.
 * - A year picker, month picker, and day picker to filter tasks based on date.
 * - A list of tasks with checkboxes to mark them as completed or incomplete.
 *
 * @param onSettingsClick A callback to navigate to the settings screen when the settings icon is clicked.
 * @param onAddNewTask A callback that triggers the action to add a new task when the floating action button is clicked.
 * @param onTaskClick A callback to handle task click events.
 * @param modifier Modifier for customizing the layout of this composable screen.
 */
@Composable
fun TasksScreen(
    onSettingsClick: () -> Unit,
    onAddNewTask: () -> Unit,
    onTaskClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    val tasksViewModel: TasksViewModel = hiltViewModel()
    val tasks by tasksViewModel.tasks.collectAsStateWithLifecycle()
    val tasksUiState by tasksViewModel.tasksUiState.collectAsStateWithLifecycle()

    TasksScreenContent(
        tasksUiState = tasksUiState,
        onAddNewTask = onAddNewTask,
        onSettingsClick = onSettingsClick,
        onTaskClick = onTaskClick,
        onTaskCheckedChange = tasksViewModel::flagTask,
        onTaskDelete = tasksViewModel::deleteTask,
        tasks = tasks,
        onYearSelected = tasksViewModel::updateSelectedYear,
        onNextMonthClicked = tasksViewModel::selectNextMonth,
        onPreviousMonthClicked = tasksViewModel::selectPreviousMonth,
        onSelectedDayInMonthChange = tasksViewModel::updateSelectDayInMonth,
        modifier = modifier
    )
}

/**
 * The content of the Tasks screen, which includes:
 * - A toolbar with settings navigation.
 * - Floating action button for adding a new task.
 * - Year, month, and day pickers to filter tasks by date.
 * - A list of tasks.
 *
 * @param tasksUiState UI state containing selected year, month, day, and tasks data.
 * @param onAddNewTask Callback triggered when the floating action button is clicked.
 * @param onSettingsClick Callback triggered when the settings icon is clicked.
 * @param onTaskClick Callback triggered when a task is clicked.
 * @param onTaskCheckedChange Callback triggered when the checkbox for a task is changed.
 * @param tasks List of tasks to display.
 * @param onYearSelected Callback triggered when a year is selected from the year picker.
 * @param onNextMonthClicked Callback triggered when the next month button is clicked.
 * @param onPreviousMonthClicked Callback triggered when the previous month button is clicked.
 * @param onSelectedDayInMonthChange Callback triggered when a day is selected from the day picker.
 * @param modifier Modifier for customizing the layout of this composable screen.
 */
@Composable
fun TasksScreenContent(
    tasksUiState: TasksUiState,
    onAddNewTask: () -> Unit,
    onSettingsClick: () -> Unit,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    tasks: List<Task>,
    onYearSelected: (Int) -> Unit,
    onNextMonthClicked: () -> Unit,
    onPreviousMonthClicked: () -> Unit,
    onSelectedDayInMonthChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TasksToolBar(onSettingsClicked = onSettingsClick)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddNewTask,
                modifier = Modifier.padding(
                    dimensionResource(
                        R.dimen.floating_action_button_padding
                    )
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(
                        R.string.floating_action_button_add_content_description
                    )
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.task_screen_vertical_spacing)
            ),
        ) {
            YearPicker(
                minYear = 1900,
                maxYear = 2100,
                selectedYear = tasksUiState.selectedYear,
                onYearSelected = onYearSelected,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.year_picker_start_and_end_padding),
                    end = dimensionResource(R.dimen.year_picker_start_and_end_padding)
                )
            )

            MonthYearPicker(
                selectedMonth = tasksUiState.selectedMonth,
                onNextMonth = onNextMonthClicked,
                onPreviousMonth = onPreviousMonthClicked,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.month_picker_start_and_end_padding),
                    end = dimensionResource(R.dimen.month_picker_start_and_end_padding)
                )

            )

            HorizontalDayPicker(
                selectedDayInMonth = tasksUiState.selectedDayInMonth,
                onSelectedDayInMonthChange = onSelectedDayInMonthChange,
                daysInMonth = tasksUiState.weekdaysAndDaysInMonth,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.horizontal_day_picker_content_padding),
                    end = dimensionResource(R.dimen.horizontal_day_picker_content_padding)
                )
            )

            TaskItemList(
                tasks = tasks,
                onTaskClick = onTaskClick,
                onTaskCheckedChange = onTaskCheckedChange,
                onTaskDelete = onTaskDelete,
                modifier = Modifier.padding(
                    start = dimensionResource(R.dimen.task_item_inner_surface_start_padding),
                    end = dimensionResource(R.dimen.task_item_inner_surface_end_padding)
                )
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksToolBar(
    onSettingsClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks),
                modifier = modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(
                onClick = onSettingsClicked
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings_24),
                    contentDescription = stringResource(id = R.string.navigate_to_settings_content_description)
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun YearPicker(
    modifier: Modifier = Modifier,
    selectedYear: Int = 2023,
    minYear: Int,
    maxYear: Int,
    onYearSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var intentionallyDismissed by remember { mutableStateOf(false) }
    var rowHeight by remember { mutableIntStateOf(0) }
    val years = (minYear..maxYear).toList()

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.onGloballyPositioned { coordinates ->
                rowHeight = coordinates.size.height
            }
        ) {
            Text(
                text = selectedYear.toString(),
                modifier = modifier

            )
            IconButton(
                onClick = {
                    intentionallyDismissed = true
                    expanded = !expanded
                }
            ) {
                Icon(
                    painter = if (expanded) painterResource(id = R.drawable.arrow_up_24)
                    else painterResource(id = R.drawable.arrow_down_24),
                    contentDescription = stringResource(R.string.select_year_content_description)
                )
            }
        }
        if (expanded) {
            Popup(
                onDismissRequest = {
                    if (!intentionallyDismissed) {
                        expanded = false
                    }
                    intentionallyDismissed = false
                }, offset = IntOffset(0, rowHeight), alignment = Alignment.TopStart
            ) {
                Surface(
                    modifier = Modifier
                        .shadow(
                            elevation = dimensionResource(R.dimen.year_grid_selector_shadow)
                        )
                        .height(
                            dimensionResource(R.dimen.year_grid_selector_height)
                        )
                ) {
                    YearGridSelector(
                        selectedYear = selectedYear,
                        years = years,
                        onYearSelected = { year ->
                            onYearSelected(year)
                            expanded = false
                        },
                        modifier = Modifier.padding(dimensionResource(R.dimen.year_grid_selector_padding))
                    )
                }
            }
        }
    }
}

@Composable
fun YearGridSelector(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3), modifier = modifier
    ) {
        items(items = years) { year ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(selected = year == selectedYear, onClick = { onYearSelected(year) })
                    .background(
                        color = if (year == selectedYear) MaterialTheme.colorScheme.surfaceContainer
                        else MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.small
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(dimensionResource(R.dimen.year_grid_selector_text_padding))
                )
            }
        }
    }
}


@Composable
fun MonthYearPicker(
    selectedMonth: String,
    onNextMonth: () -> Unit,
    onPreviousMonth: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousMonth) {
            Icon(
                painter = painterResource(id = R.drawable.navigate_previous_24),
                contentDescription = stringResource(R.string.navigate_previous_month_content_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Text(
            text = selectedMonth,
            style = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        IconButton(onClick = onNextMonth) {
            Icon(
                painter = painterResource(id = R.drawable.navigate_next_24),
                contentDescription = stringResource(R.string.navigate_next_month_content_description),
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }
}

@Composable
fun WeekdayAndDayOfMonthItem(
    weekday: String,
    dayOfMonth: String,
    selectedDayInMonth: String,
    onDayOfMonthClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = dayOfMonth == selectedDayInMonth
    Surface(
        border = BorderStroke(
            width = if (isSelected) dimensionResource(R.dimen.weekday_and_day_of_month_item_border_width) else 0.dp,
            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
        ),
        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
        else MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(dimensionResource(R.dimen.weekday_and_day_of_month_item_padding))
                .width(dimensionResource(R.dimen.weekday_and_day_of_month_width))
                .clickable { onDayOfMonthClick(dayOfMonth) }
        ) {
            Text(
                text = weekday,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = dayOfMonth,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun HorizontalDayPicker(
    selectedDayInMonth: String,
    onSelectedDayInMonthChange: (String) -> Unit,
    daysInMonth: List<Pair<String, String>>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(selectedDayInMonth) {
        val selectedIndex = daysInMonth.indexOfFirst { it.second == selectedDayInMonth }
        if (selectedIndex != -1) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(dimensionResource(R.dimen.horizontal_day_picker_content_padding)),
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.horizontal_day_picker_horizontal_spacing)),
        modifier = modifier.fillMaxWidth()
    ) {
        items(daysInMonth) { (weekday, dayOfMonth) ->
            WeekdayAndDayOfMonthItem(
                onDayOfMonthClick = onSelectedDayInMonthChange,
                weekday = weekday,
                dayOfMonth = dayOfMonth,
                selectedDayInMonth = selectedDayInMonth
            )
        }
    }
}


@Composable
fun DismissBackground(dismissDirection: SwipeToDismissBoxValue?) {
    val backgroundColor = when (dismissDirection) {
        SwipeToDismissBoxValue.EndToStart -> Color(0xFFFF1744) // Red for delete
        else -> Color.Transparent // Default or no action
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (dismissDirection == SwipeToDismissBoxValue.EndToStart) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.White
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onTaskClick: () -> Unit,
    onTaskCheckedChange: () -> Unit,
    onTaskDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> {
                    showDialog = true // Trigger dialog when swiped
                    false // Prevent immediate deletion
                }

                else -> false
            }
        },
        positionalThreshold = { it * 0.25f } // Threshold for swipe
    )

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(stringResource(R.string.delete_task))
            },
            text = {
                Text(stringResource(R.string.delete_task_confirmation))
            },
            confirmButton = {
                TextButton(onClick = {
                    onTaskDelete()
                    showDialog = false
                    SnackBarManager.showMessage(R.string.item_deleted)
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    SwipeToDismissBox(
        state = dismissState,
        modifier = modifier.clip(shape = MaterialTheme.shapes.small),
        backgroundContent = { DismissBackground(dismissState.dismissDirection) },
        content = {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier.clickable { onTaskClick() }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = modifier.padding(start = dimensionResource(R.dimen.task_item_inner_surface_start_padding))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = modifier
                                .weight(1f)
                                .padding(dimensionResource(R.dimen.task_item_content_padding))
                        ) {
                            Text(
                                text = task.title,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.task_item_time_and_icon_horizontal_spacing))
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.access_time_24),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                Text(
                                    text = task.dueTime,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        IconButton(
                            onClick = onTaskCheckedChange,
                            modifier = modifier
                                .padding(end = dimensionResource(R.dimen.task_item_checkbox_end_padding))
                                .border(
                                    width = dimensionResource(R.dimen.task_item_checkbox_border_width),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    shape = CircleShape
                                )
                                .background(
                                    color = if (task.completed) MaterialTheme.colorScheme.primary.copy(
                                        alpha = 0.7f
                                    )
                                    else Color.Transparent,
                                    shape = CircleShape
                                )
                                .size(dimensionResource(R.dimen.task_item_checkbox_size))
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.check_circle_24),
                                contentDescription = null,
                                tint = if (task.completed) MaterialTheme.colorScheme.onPrimary
                                else Color.Transparent
                            )
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun TaskItemList(
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task) -> Unit,
    onTaskDelete: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(dimensionResource(R.dimen.task_item_list_vertical_content_padding)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.task_item_list_vertical_spacing)),
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = tasks, key = { task -> task.id }) { task ->
            TaskItem(
                task = task,
                onTaskClick = { onTaskClick(task) },
                onTaskCheckedChange = { onTaskCheckedChange(task) },
                onTaskDelete = { onTaskDelete(task) }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TaskItemPreview() {
    TaskMinderTheme {
        TaskItem(
            task = Task(
                id = "1",
                title = "Title",
                priority = "High",
                dueDate = "01/01/2023",
                dueTime = "12:00",
                description = "Description",
                completed = true,
                alert = false,
                userId = "2"
            ),
            onTaskClick = {},
            onTaskCheckedChange = {},
            onTaskDelete = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun WeekdayAndDayOfMonthPreview() {
    TaskMinderTheme {
        WeekdayAndDayOfMonthItem(
            weekday = "Mon", dayOfMonth = "02",
            onDayOfMonthClick = {},
            selectedDayInMonth = "02"
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true, showSystemUi = true)
@Composable
private fun TasksScreenContentPreview() {
    TaskMinderTheme {
        TasksScreenContent(
            onAddNewTask = {},
            onSettingsClick = {},
            onTaskClick = {},
            onTaskCheckedChange = {},
            tasks = listOf(),
            tasksUiState = TasksUiState(),
            onYearSelected = {},
            onNextMonthClicked = {},
            onPreviousMonthClicked = {},
            onSelectedDayInMonthChange = {},
            onTaskDelete = {}
        )
    }
}