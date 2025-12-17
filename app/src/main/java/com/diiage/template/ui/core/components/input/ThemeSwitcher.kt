package com.diiage.template.ui.core.components.input

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diiage.template.ui.core.theme.AppTheme
import com.diiage.template.ui.core.theme.ThemeManager
import com.diiage.template.ui.core.theme.ThemeState
import com.diiage.template.R.drawable.ic_sun
import com.diiage.template.R.drawable.ic_moon
import com.diiage.template.R.drawable.ic_eye
import androidx.compose.ui.graphics.ColorFilter

/**
 * ThemeSwitcher is a composable that provides a dropdown menu for selecting the app's theme.
 *
 * It allows users to switch between Light, Dark, and System themes.
 *
 * @param modifier Modifier to be applied to the ThemeSwitcher.
 * @param currentTheme The currently selected theme state.
 * @param onThemeChanged Optional callback invoked when the theme is changed.
 */
@Composable
fun ThemeSwitcher(
    modifier: Modifier = Modifier,
    currentTheme: ThemeState = ThemeManager.themeState,
    onThemeChanged: (() -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        // Dropdown trigger
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { expanded = true },
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Image(
                    painter = when (currentTheme) {
                        ThemeState.Light -> painterResource(ic_sun)
                        ThemeState.Dark -> painterResource(ic_moon)
                        ThemeState.System -> painterResource(ic_eye)
                    },
                    contentDescription = "Icône du thème actuel",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .width(20.dp)
                )
                Text(
                    text = when (currentTheme) {
                        ThemeState.Light -> "Clair"
                        ThemeState.Dark -> "Sombre"
                        ThemeState.System -> "Système"
                    },
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Sélectionner le thème",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .clip(RoundedCornerShape(8.dp))
        ) {
            // Light theme option
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(ic_sun),
                            contentDescription = "Icône thème clair",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .width(20.dp)
                        )
                        Text("Clair", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                onClick = {
                    ThemeManager.setTheme(ThemeState.Light)
                    expanded = false
                    onThemeChanged?.invoke()
                }
            )

            // Dark theme option
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(ic_moon),
                            contentDescription = "Icône thème sombre",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .width(20.dp)
                        )
                        Text("Sombre", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                onClick = {
                    ThemeManager.setTheme(ThemeState.Dark)
                    expanded = false
                    onThemeChanged?.invoke()
                }
            )

            // System theme option
            DropdownMenuItem(
                text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(ic_eye),
                            contentDescription = "Icône thème système",
                            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .width(20.dp)
                        )
                        Text("Système", color = MaterialTheme.colorScheme.onPrimary)
                    }
                },
                onClick = {
                    ThemeManager.setTheme(ThemeState.System)
                    expanded = false
                    onThemeChanged?.invoke()
                }
            )
        }
    }
}

@Preview(showBackground = true, name = "Light Theme - Light Mode")
@Composable
fun ThemeSwitcherPreview_LightMode() {
    // Force light theme for this preview
    ThemeManager.setTheme(ThemeState.Light)

    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                ThemeSwitcher(
                    onThemeChanged = { /* Preview doesn't need action */ }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Light Theme - System Mode")
@Composable
fun ThemeSwitcherPreview_SystemMode() {
    // Force system theme for this preview
    ThemeManager.setTheme(ThemeState.System)

    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                ThemeSwitcher(
                    onThemeChanged = { /* Preview doesn't need action */ }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dark Theme - Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemeSwitcherDarkPreview_DarkMode() {
    // Force dark theme for this preview
    ThemeManager.setTheme(ThemeState.Dark)

    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                ThemeSwitcher(
                    onThemeChanged = { /* Preview doesn't need action */ }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Dark Theme - System Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ThemeSwitcherDarkPreview_SystemMode() {
    // Force system theme for this preview
    ThemeManager.setTheme(ThemeState.System)

    AppTheme {
        Surface {
            Box(modifier = Modifier.padding(16.dp)) {
                ThemeSwitcher(
                    onThemeChanged = { /* Preview doesn't need action */ }
                )
            }
        }
    }
}