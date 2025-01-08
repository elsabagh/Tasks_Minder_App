package com.example.Tasks_Minder_App

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Tasks_Minder_App.data.service.LogService
import com.example.Tasks_Minder_App.presentation.common.snackbar.SnackBarManager
import com.example.Tasks_Minder_App.presentation.common.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A base ViewModel for managing background tasks and handling exceptions in a centralized way.
 *
 * This ViewModel provides the `launchCatching` function, which launches a coroutine and handles exceptions
 * by showing a snackBar message (optional) and logging the error using the provided [logService].
 *
 * @property logService The service used for logging non-fatal crashes.
 */
open class TaskMinderViewModel(private val logService: LogService) : ViewModel() {

    /**
     * Launches a coroutine that catches any exceptions and handles them by showing a snackBar message
     * (optional) and logging the exception to the [logService].
     *
     * @param snackBar A boolean flag indicating whether the error should be displayed in a snackBar.
     *        Defaults to true.
     * @param block The suspend block of code to execute within the coroutine.
     */
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}