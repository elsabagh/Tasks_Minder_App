package com.example.Tasks_Minder_App.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.Tasks_Minder_App.TaskMinderAppState
import com.example.Tasks_Minder_App.presentation.screen.account.AccountScreen
import com.example.Tasks_Minder_App.presentation.screen.editTask.EditTaskScreen
import com.example.Tasks_Minder_App.presentation.screen.splash.SplashScreen
import com.example.Tasks_Minder_App.presentation.screen.task.TasksScreen
import com.example.Tasks_Minder_App.presentation.screen.theme.ThemeScreen

/**
 * A composable that sets up the navigation graph for the TaskMinder app.
 *
 * This composable hosts all the destinations and screens within the app and manages navigation
 * between them. It uses a `NavController` from the `TaskMinderAppState` to handle navigation actions.
 *
 * @param appState The app state that provides access to the `NavController` and navigation helper functions.
 * @param modifier An optional modifier for the NavHost container.
 */
@Composable
fun TaskMinderNavHost(
    appState: TaskMinderAppState,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = appState.navController,
        startDestination = SplashDestination.route,
        modifier = modifier
    ) {
        // Splash Screen - First screen shown to the user when the app starts
        composable(route = SplashDestination.route) {
            SplashScreen(
                onAppStart = {
                    // On app start, navigate to the Tasks screen and pop up to the Splash screen
                    appState.navigateSingleTopToAndPopupTo(
                        route = TasksDestination.route,
                        popUpToRoute = SplashDestination.route
                    )
                }
            )
        }

//        // Login Screen - Screen where users can sign in
//        composable(route = LoginDestination.route) {
//            LoginScreen(
//                onSignInClick = {
//                    // After sign-in, navigate to the Tasks screen and pop up to the Tasks destination
//                    appState.navigateSingleTopToAndPopupTo(
//                        route = TasksDestination.route,
//                        popUpToRoute = TasksDestination.route
//                    )
//                }
//            )
//        }
//
//        // Sign Up Screen - Screen where users can sign up for a new account
//        composable(route = SignUpDestination.route) {
//            SignUpScreen(
//                onSignUpClick = {
//                    // After sign-up, navigate to the Tasks screen and pop up to the Tasks destination
//                    appState.navigateSingleTopToAndPopupTo(
//                        route = TasksDestination.route,
//                        popUpToRoute = TasksDestination.route
//                    )
//                }
//            )
//        }

        // Tasks Screen - Main screen showing a list of tasks
        composable(route = TasksDestination.route) {
            TasksScreen(
                onAddNewTask = { appState.navigate("${EditTaskDestination.routeWithArgs}?${EditTaskDestination.TASK_ID_ARG}=") },
                onSettingsClick = { appState.navigate(SettingsDestination.route) },
                onTaskClick = { appState.navigate("${EditTaskDestination.route}/${it.id}") }
            )
        }

        //Edit Task Screen - Screen to edit or add a new task
        composable(
            route = EditTaskDestination.routeWithArgs,
            arguments = EditTaskDestination.arguments
        ) {
            EditTaskScreen(
                navigateBack = { appState.popUp() },
                onTaskSaved = { appState.popUp() }
            )
        }

//        // Settings Screen - Screen to manage app settings
//        composable(route = SettingsDestination.route) {
//            SettingsScreen(
//                onNavigateUp = { appState.popUp() },
//                onNavigateToAccount = {
//                    appState.navigate(AccountDestination.route)
//                },
//                onNavigateToTheme = { appState.navigate(ThemeDestination.route) }
//            )
//        }

        // Theme Screen - Screen to modify the app theme settings
        composable(route = ThemeDestination.route) {
            ThemeScreen(
                onNavigateUp = { appState.popUp() }
            )
        }

        // Account Screen - Screen to manage user account details
        composable(route = AccountDestination.route) {
            AccountScreen(
                onLoginClick = { appState.navigate(LoginDestination.route) },
                onSignUpClick = { appState.navigate(SignUpDestination.route) },
                onNavigateUp = { appState.popUp() },
                onSignOutClick = {
                    // Navigate to the Splash screen after sign-out and pop up all previous screens
                    appState.navigateSingleTopToAndPopupTo(
                        route = SplashDestination.route,
                        popUpToRoute = SplashDestination.route
                    )
                },
                onDeleteMyAccountClick = {
                    // Navigate to the Splash screen after account deletion and pop up all previous screens
                    appState.navigateSingleTopToAndPopupTo(
                        route = SplashDestination.route,
                        popUpToRoute = SplashDestination.route
                    )
                }
            )
        }
    }
}