package com.example.Tasks_Minder_App.presentation.screen.task

import java.util.Calendar

/**
 * A data class representing the state of the task calendar screen.
 *
 * This class holds information about the currently selected year, month, day,
 * and the list of weekdays and corresponding days in the month. It is designed to
 * facilitate displaying a calendar-like UI for task management.
 *
 * @param selectedYear The currently selected year. Defaults to the current year.
 * @param selectedMonthIndex The index of the currently selected month (0 for January, 1 for February, etc.). Defaults to the current month.
 * @param selectedDayInMonth The currently selected day in the month, represented as a string. Defaults to the current day of the month.
 * @param weekdaysAndDaysInMonth A list of pairs, where each pair consists of a weekday name (e.g., "Monday") and the corresponding day of the month (e.g., "1"). Defaults to an empty list.
 */
//TODO: remove unnecessary code
data class TasksUiState(
//    val selectedYear: Int = Calendar.getInstance().get(Calendar.YEAR),
    val selectedYear: Int = defaultYear,
//    val selectedMonthIndex: Int = Calendar.getInstance().get(Calendar.MONTH),
    val selectedMonthIndex: Int = defaultMonthIndex,
//    val selectedDayInMonth: String = Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString(),
    val selectedDayInMonth: String = defaultDayInMonth.toString(),
    val weekdaysAndDaysInMonth: List<Pair<String, String>> = emptyList(),
) {

    companion object {
        // Default values based on the current date
        private val calendar = Calendar.getInstance()

        // Default year, current year
        val defaultYear: Int = calendar.get(Calendar.YEAR)

        // Default month index, current month (0-indexed)
        val defaultMonthIndex: Int = calendar.get(Calendar.MONTH)

        // Default day in month, current day (as an Int for easier comparison)
        val defaultDayInMonth: Int = calendar.get(Calendar.DAY_OF_MONTH)
    }

    // List of month names for lookup based on the month index
    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    /**
     * Returns the name of the selected month corresponding to [selectedMonthIndex].
     * For example, if [selectedMonthIndex] is 0, it returns "January".
     */
    val selectedMonth: String
        get() = months[selectedMonthIndex]
}