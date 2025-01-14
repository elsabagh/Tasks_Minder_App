package com.example.Tasks_Minder_App.utils

object Constants {
    const val SPLASH_SCREEN_TIMEOUT = 1000L

    /**
     * The name of the Firestore collection where tasks are stored.
     */
    const val TASKS_COLLECTION = "tasks"

    /**
     * The field name in the Firestore task documents that stores the user's unique identifier.
     */
    const val USER_ID_FIELD = "userId"

    /**
     * The field name in the Firestore task documents that stores the due date of the task.
     */
    const val TASK_DATE_FIELD = "dueDate"

    /**
     * The trace name used for monitoring and debugging task-saving operations.
     */
    const val SAVE_TASK_TRACE = "saveTask"

    /**
     * The trace name used for monitoring and debugging task-updating operations.
     */
    const val UPDATE_TASK_TRACE = "updateTask"

    /**
     * Trace tag for linking accounts, useful for debugging or monitoring.
     */
    const val LINK_ACCOUNT_TRACE = "linkAccount"

    const val REQUEST_CODE_SCHEDULE_EXACT_ALARM = 1001


}