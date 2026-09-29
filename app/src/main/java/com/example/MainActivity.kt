package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.AppNavigationRail
import com.example.ui.screens.achievements.AchievementsScreen
import com.example.ui.screens.ai.ScenarioGeneratorScreen
import com.example.ui.screens.auth.AuthScreen
import com.example.ui.screens.dashboard.StudentDashboardScreen
import com.example.ui.screens.mission.MissionActivityScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.screens.result.MissionResultScreen
import com.example.ui.screens.roleplay.RoleplayScreen
import com.example.ui.screens.teacher.TeacherDashboardScreen
import com.example.ui.screens.vocab.VocabularyScreen
import com.example.ui.screens.world.WorldMapScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.ArabicScenarioGeneratorViewModel
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val scenarioAiViewModel: ArabicScenarioGeneratorViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(
                    viewModel = viewModel,
                    scenarioAiViewModel = scenarioAiViewModel
                )
            }
        }
    }
}

@Composable
fun MainAppContent(
    viewModel: MainViewModel,
    scenarioAiViewModel: ArabicScenarioGeneratorViewModel
) {
    val currentScreen by viewModel.currentScreen.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    val isTeacher = currentUser?.role == "TEACHER"

    when (currentScreen) {
        ScreenState.AUTH -> {
            AuthScreen(viewModel = viewModel)
        }

        ScreenState.MISSION_ACTIVE -> {
            MissionActivityScreen(viewModel = viewModel)
        }

        ScreenState.MISSION_RESULT -> {
            MissionResultScreen(viewModel = viewModel)
        }

        ScreenState.AI_ROLEPLAY -> {
            RoleplayScreen(viewModel = viewModel)
        }

        ScreenState.AI_SCENARIO_GENERATOR -> {
            ScenarioGeneratorScreen(
                viewModel = scenarioAiViewModel,
                onBackToHome = { viewModel.navigateTo(ScreenState.DASHBOARD) }
            )
        }

        else -> {
            // Adaptive Layout: Laptop & Wide Tablet vs Handphone
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val isWideScreen = maxWidth >= 600.dp

                if (isWideScreen) {
                    // Laptop / Tablet Layout: Side Navigation Rail + Full Screen Content Area
                    Row(modifier = Modifier.fillMaxSize()) {
                        AppNavigationRail(
                            viewModel = viewModel,
                            currentScreen = currentScreen,
                            isTeacher = isTeacher
                        )

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                        ) {
                            when (currentScreen) {
                                ScreenState.DASHBOARD -> StudentDashboardScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                ScreenState.WORLD_MAP -> WorldMapScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                ScreenState.VOCABULARY -> VocabularyScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                ScreenState.ACHIEVEMENTS -> AchievementsScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                ScreenState.PROFILE -> ProfileScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                ScreenState.TEACHER_DASHBOARD -> TeacherDashboardScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                                else -> StudentDashboardScreen(viewModel = viewModel, modifier = Modifier.fillMaxSize())
                            }
                        }
                    }
                } else {
                    // Handphone Layout: Bottom Navigation Bar + Vertical Inset Padding
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            AppBottomNavigation(
                                viewModel = viewModel,
                                currentScreen = currentScreen,
                                isTeacher = isTeacher
                            )
                        }
                    ) { innerPadding ->
                        val contentModifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)

                        when (currentScreen) {
                            ScreenState.DASHBOARD -> StudentDashboardScreen(viewModel = viewModel, modifier = contentModifier)
                            ScreenState.WORLD_MAP -> WorldMapScreen(viewModel = viewModel, modifier = contentModifier)
                            ScreenState.VOCABULARY -> VocabularyScreen(viewModel = viewModel, modifier = contentModifier)
                            ScreenState.ACHIEVEMENTS -> AchievementsScreen(viewModel = viewModel, modifier = contentModifier)
                            ScreenState.PROFILE -> ProfileScreen(viewModel = viewModel, modifier = contentModifier)
                            ScreenState.TEACHER_DASHBOARD -> TeacherDashboardScreen(viewModel = viewModel, modifier = contentModifier)
                            else -> StudentDashboardScreen(viewModel = viewModel, modifier = contentModifier)
                        }
                    }
                }
            }
        }
    }
}
