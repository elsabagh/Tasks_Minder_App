package com.example.Tasks_Minder_App.presentation.screen.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.TaskMinderViewModel
import com.example.Tasks_Minder_App.data.service.AccountService
import com.example.Tasks_Minder_App.data.service.LogService
import com.example.Tasks_Minder_App.presentation.common.ext.isEmailValid
import com.example.Tasks_Minder_App.presentation.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService
) : TaskMinderViewModel(logService) {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val email
        get() = _uiState.value.email

    private val password
        get() = _uiState.value.password

    private val _isSignInSucceeded = MutableStateFlow(false)
    val isSignInSucceeded: StateFlow<Boolean> = _isSignInSucceeded.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.value = _uiState.value.copy(
            email = email
        )
    }

    fun onPasswordChange(password: String) {
        _uiState.value = _uiState.value.copy(
            password = password
        )
    }

    fun signInToAccount() {
        if (!email.isEmailValid()) {
            SnackBarManager.showMessage(R.string.email_error)
            return
        }
        if (password.isBlank()) {
            SnackBarManager.showMessage(R.string.password_error)
            return
        }
        viewModelScope.launch {
            accountService.authenticate(email, password)
            _isSignInSucceeded.value = true

        }

    }

    fun resetIsSignInSucceeded() {
        _isSignInSucceeded.value = false
    }
}