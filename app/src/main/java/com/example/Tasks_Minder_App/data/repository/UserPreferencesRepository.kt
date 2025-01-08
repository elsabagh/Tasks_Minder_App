package com.example.Tasks_Minder_App.data.repository

import com.example.Tasks_Minder_App.data.model.ThemeColor
import com.example.Tasks_Minder_App.data.model.ThemeMode
import com.example.Tasks_Minder_App.data.model.ThemePreferences
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user theme preferences.
 */
interface UserPreferencesRepository {
    /**
     * A [Flow] representing the current state of the user's selected theme preferences.
     * Emits updates whenever the preferences change.
     */
    val selectedThemeState: Flow<ThemePreferences>

    /**
     * Updates the user's selected theme color.
     *
     * @param themeColor The new theme color to save.
     */

    suspend fun updateThemeColor(themeColor: ThemeColor)
    /**
     * Updates the user's selected theme mode (e.g., Light, Dark).
     *
     * @param themeMode The new theme mode to save.
     */
    suspend fun updateThemeMode(themeMode: ThemeMode)
}