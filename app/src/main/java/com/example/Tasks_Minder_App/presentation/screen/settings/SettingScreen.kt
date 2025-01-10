package com.example.Tasks_Minder_App.presentation.screen.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.Tasks_Minder_App.R
import com.example.Tasks_Minder_App.data.model.ThemeMode
import com.example.Tasks_Minder_App.presentation.common.components.ActionToolbar
import com.example.Tasks_Minder_App.presentation.screen.theme.ThemeViewModel
import com.example.Tasks_Minder_App.presentation.ui.theme.TaskMinderTheme

@Composable
fun SettingsScreen(
    onNavigateUp: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToTheme: () -> Unit
) {

    val onNavigateToAccountMemoized = remember { onNavigateToAccount }
    val onNavigateToThemeMemoized = remember { onNavigateToTheme }
    val onNavigateUpMemoized = remember { onNavigateUp }

    val themeViewModel = hiltViewModel<ThemeViewModel>()
    val themeState by themeViewModel.themeState.collectAsStateWithLifecycle()
    val isDarkTheme = themeState.mode == ThemeMode.DARK

    SettingsScreenContent(
        isDarkTheme = isDarkTheme,
        onNavigateUp = onNavigateUpMemoized,
        onNavigateToAccount = onNavigateToAccountMemoized,
        onNavigateToTheme = onNavigateToThemeMemoized
    )
}

@Composable
fun SettingsScreenContent(
    themeViewModel: ThemeViewModel = hiltViewModel(),
    isDarkTheme: Boolean,
    onNavigateUp: () -> Unit,
    onNavigateToAccount: () -> Unit,
    onNavigateToTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            ActionToolbar(
                title = R.string.settings,
                onNavigateUp = onNavigateUp,
                modifier = modifier
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                dimensionResource(R.dimen.settings_screen_vertical_spacing)
            )
        ) {
            SettingsItemRow(
                icon = R.drawable.person_outline_24,
                text = R.string.account,
                endContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.navigate_next_24),
                        contentDescription = stringResource(R.string.navigate_to_account_content_description),
                        modifier = modifier.clickable {
                            onNavigateToAccount()
                        }
                    )
                }
            )
            SettingsItemRow(
                icon = R.drawable.theme,
                text = R.string.theme,
                endContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.navigate_next_24),
                        contentDescription = stringResource(R.string.navigate_to_theme_content_description),
                        modifier = modifier.clickable {
                            onNavigateToTheme()
                        }
                    )
                }
            )
            SettingsItemRow(
                icon = R.drawable.sun,
                text = R.string.change_mode,
                endContent = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = {
                            themeViewModel.setThemeMode(
                                if (isDarkTheme) ThemeMode.LIGHT else ThemeMode.DARK
                            )
                        }

                    )
                }
            )
        }
    }


}

@Composable
fun SettingsItemRow(
    @DrawableRes icon: Int,
    @StringRes text: Int,
    endContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            dimensionResource(
                R.dimen.settings_row_horizontal_spacing
            )
        )
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(dimensionResource(R.dimen.leading_icon_size))
        )

        Text(
            text = stringResource(text),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
        endContent()
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingsItemRowPreview() {
    TaskMinderTheme {
        SettingsItemRow(
            icon = R.drawable.sun,
            text = R.string.account,
            endContent = {}
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent(
        isDarkTheme = false,
        onNavigateUp = {},
        onNavigateToAccount = {},
        onNavigateToTheme = {})
}
