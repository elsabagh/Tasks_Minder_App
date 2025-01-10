package com.example.Tasks_Minder_App.presentation.screen.signup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.presentation.common.components.BasicToolbar
import com.example.Tasks_Minder_App.presentation.common.components.EmailField
import com.example.Tasks_Minder_App.presentation.common.components.PasswordField
import com.example.Tasks_Minder_App.presentation.common.ext.buttonModifier
import com.example.Tasks_Minder_App.presentation.common.ext.fieldModifier
import com.example.Tasks_Minder_App.presentation.ui.theme.TaskMinderTheme

@Composable
fun SignUpScreen(
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val signUpViewModel: SignUpViewModel = hiltViewModel()
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()
    val isAccountCreated by signUpViewModel.isAccountCreated.collectAsStateWithLifecycle()

    LaunchedEffect(isAccountCreated) {
        if (isAccountCreated) {
            onSignUpClick()
            signUpViewModel.resetIsAccountCreated()
        }
    }

    val onSignUpClickMemoized: () -> Unit = remember { signUpViewModel::createAccount }
    val onEmailChange: (String) -> Unit = remember { signUpViewModel::onEmailChange }
    val onPasswordChange: (String) -> Unit = remember { signUpViewModel::onPasswordChange }
    val onConfirmPasswordChange: (String) -> Unit =
        remember { signUpViewModel::onConfirmPasswordChange }

    SignUpScreenContent(
        uiState = uiState,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onConfirmPasswordChange = onConfirmPasswordChange,
        onSignUpClick = onSignUpClickMemoized
    )
}

@Composable
fun SignUpScreenContent(
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            BasicToolbar(
                title = R.string.create_account,
                modifier = modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            EmailField(
                value = uiState.email,
                onNewValue = onEmailChange,
                modifier = modifier.fieldModifier()
            )
            PasswordField(
                value = uiState.password,
                placeholder = R.string.password,
                onNewValue = onPasswordChange,
                modifier = modifier.fieldModifier()
            )
            PasswordField(
                value = uiState.confirmPassword,
                placeholder = R.string.repeat_password,
                onNewValue = onConfirmPasswordChange,
                modifier = modifier.fieldModifier()
            )

            Button(
                onClick = onSignUpClick,
                modifier = modifier.buttonModifier()
            ) {
                Text(text = stringResource(R.string.create_account))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SignUpScreenPreview() {
    TaskMinderTheme {
        SignUpScreenContent(
            onSignUpClick = {},
            uiState = TODO(),
            onEmailChange = TODO(),
            onPasswordChange = TODO(),
            onConfirmPasswordChange = TODO(),
            modifier = TODO()
        )
    }
}