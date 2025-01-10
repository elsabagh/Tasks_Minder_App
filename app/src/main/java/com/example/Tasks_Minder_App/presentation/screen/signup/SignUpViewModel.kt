package com.example.Tasks_Minder_App.presentation.screen.signup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.TaskMinderViewModel
import com.example.Tasks_Minder_App.data.service.AccountService
import com.example.Tasks_Minder_App.data.service.LogService
import com.example.Tasks_Minder_App.presentation.common.ext.isEmailValid
import com.example.Tasks_Minder_App.presentation.common.ext.isPasswordValid
import com.example.Tasks_Minder_App.presentation.common.ext.passwordMatches
import com.example.Tasks_Minder_App.presentation.common.snackbar.SnackBarManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService
) : TaskMinderViewModel(logService) {

    private var _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val email
        get() = _uiState.value.email

    private val password
        get() = _uiState.value.password


    private var _isAccountCreated = MutableStateFlow(false)
    val isAccountCreated: StateFlow<Boolean> = _isAccountCreated.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            email = newValue
        )
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            password = newValue
        )
    }

    fun onConfirmPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(
            confirmPassword = newValue
        )
    }

    fun createAccount() {
        if (!email.isEmailValid()) {
            SnackBarManager.showMessage(R.string.email_error)
            return
        }

        if (!password.isPasswordValid()) {
            SnackBarManager.showMessage(R.string.empty_password_error)
            return
        }

        if (!password.passwordMatches(_uiState.value.confirmPassword)) {
            SnackBarManager.showMessage(R.string.password_match_error)
            return
        }
        viewModelScope.launch {
            accountService.linkAccount(email, password)
            _isAccountCreated.value = true
        }

    }

    fun resetIsAccountCreated() {
        _isAccountCreated.value = false
    }

}