package com.example.ui.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold700
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun AppBottomNavigation(
    viewModel: MainViewModel,
    currentScreen: ScreenState,
    isTeacher: Boolean,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .testTag("bottom_nav_bar"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        // Home / Dashboard
        NavigationBarItem(
            selected = currentScreen == ScreenState.DASHBOARD,
            onClick = { viewModel.navigateTo(ScreenState.DASHBOARD) },
            icon = { Icon(Icons.Default.Home, contentDescription = "الرئيسية", modifier = Modifier.size(22.dp)) },
            label = { Text("الرئيسية", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Emerald800,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // World Map (العالم العربي)
        NavigationBarItem(
            selected = currentScreen == ScreenState.WORLD_MAP,
            onClick = { viewModel.navigateTo(ScreenState.WORLD_MAP) },
            icon = { Icon(Icons.Default.Public, contentDescription = "العالم العربي", modifier = Modifier.size(22.dp)) },
            label = { Text("العالم", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Emerald800,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Learn / Vocab (التعلم)
        NavigationBarItem(
            selected = currentScreen == ScreenState.VOCABULARY,
            onClick = { viewModel.navigateTo(ScreenState.VOCABULARY) },
            icon = { Icon(Icons.Default.MenuBook, contentDescription = "التعلم", modifier = Modifier.size(22.dp)) },
            label = { Text("التعلم", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Emerald800,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )

        // Achievements (الإنجازات)
        NavigationBarItem(
            selected = currentScreen == ScreenState.ACHIEVEMENTS,
            onClick = { viewModel.navigateTo(ScreenState.ACHIEVEMENTS) },
            icon = { Icon(Icons.Default.CardGiftcard, contentDescription = "الإنجازات", modifier = Modifier.size(22.dp)) },
            label = { Text("الإنجازات", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Gold700,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        if (isTeacher) {
            // Teacher Dashboard
            NavigationBarItem(
                selected = currentScreen == ScreenState.TEACHER_DASHBOARD,
                onClick = { viewModel.navigateTo(ScreenState.TEACHER_DASHBOARD) },
                icon = { Icon(Icons.Default.SupervisorAccount, contentDescription = "المعلم", modifier = Modifier.size(22.dp)) },
                label = { Text("المعلم", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Emerald800,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }

        // Profile (الملف الشخصي)
        NavigationBarItem(
            selected = currentScreen == ScreenState.PROFILE,
            onClick = { viewModel.navigateTo(ScreenState.PROFILE) },
            icon = { Icon(Icons.Default.Person, contentDescription = "الملف الشخصي", modifier = Modifier.size(22.dp)) },
            label = { Text("الملف", fontSize = 10.sp, fontWeight = FontWeight.Medium) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Emerald800,
                indicatorColor = MaterialTheme.colorScheme.primaryContainer
            )
        )
    }
}
