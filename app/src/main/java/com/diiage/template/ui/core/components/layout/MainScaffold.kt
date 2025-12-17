package com.diiage.template.ui.core.components.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.navigation.NavController
import com.diiage.template.ui.core.components.input.ThemeSwitcher
import com.diiage.template.ui.core.theme.ThemeManager

/**
 * Main scaffold composable that provides a consistent layout with a top app bar.
 *
 * @param navController Navigation controller for handling navigation actions.
 * @param title Title to be displayed in the top app bar.
 * @param showThemeSwitcher Whether to show the theme switcher in the app bar.
 * @param onThemeChanged Callback when theme is changed.
 * @param content Composable content to be displayed within the scaffold.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    navController: NavController,
    title: String = "Anime Explorer",
    showThemeSwitcher: Boolean = true,
    onThemeChanged: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = title)
                },
                navigationIcon = {
                    if (navController.previousBackStackEntry != null) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    }
                },
                actions = {
                    if (showThemeSwitcher) {
                        // Theme switcher in app bar actions
                        Row(
                            verticalAlignment = CenterVertically
                        ) {
                            SmallSpacer()
                            ThemeSwitcher(
                                currentTheme = ThemeManager.themeState,
                                onThemeChanged = onThemeChanged
                            )
                            SmallSpacer()
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        content = content
    )
}
