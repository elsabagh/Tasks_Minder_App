package com.example.Tasks_Minder_App.data.service

/**
 * A service interface for logging non-fatal crashes or exceptions to a crash reporting tool.
 */
interface LogService {
    /**
     * Logs a non-fatal crash or exception.
     *
     * @param throwable The exception or error to be logged.
     */
    fun logNonFatalCrash(throwable: Throwable)
}