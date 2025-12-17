package com.diiage.template.ui.screens.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.diiage.template.ui.core.Destination
import com.diiage.template.ui.core.components.input.PrimaryButton
import com.diiage.template.ui.core.components.layout.*
import com.diiage.template.ui.core.navigate

@Composable
fun HomeScreen(navController: NavController) {
    MainScaffold(navController = navController) { innerPadding ->
        CenteredBox(
            horizontalPadding = 16.dp
        ) {
            CenteredColumn(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Bienvenue dans EduSec!",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                )

                ExtraLargeSpacer()

                MediumSpacer()

                PrimaryButton(
                    onClick = {
                        navController.navigate(Destination.Splash)
                    },
                    text = "Redemarrer l'application"
                )
            }
        }
    }
}