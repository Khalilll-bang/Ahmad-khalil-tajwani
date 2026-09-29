package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SupervisorAccount
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.VoiceGenderPreference
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun AppNavigationRail(
    viewModel: MainViewModel,
    currentScreen: ScreenState,
    isTeacher: Boolean,
    modifier: Modifier = Modifier
) {
    val voicePref by viewModel.audioService.voicePreference.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()

    NavigationRail(
        modifier = modifier
            .fillMaxHeight()
            .testTag("app_navigation_rail"),
        containerColor = MaterialTheme.colorScheme.surface,
        header = {
            Column(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Branded App Logo
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Emerald800),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "ع", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Gold700)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "عَرَبِيٌّ فِي حَيَاتِي",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Emerald800,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Global Voice Mode Selector Chip
                Surface(
                    onClick = {
                        val nextPref = when (voicePref) {
                            VoiceGenderPreference.CONTEXT_AWARE -> VoiceGenderPreference.FORCE_MALE
                            VoiceGenderPreference.FORCE_MALE -> VoiceGenderPreference.FORCE_FEMALE
                            VoiceGenderPreference.FORCE_FEMALE -> VoiceGenderPreference.CONTEXT_AWARE
                        }
                        viewModel.audioService.setVoicePreference(nextPref)
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = when (voicePref) {
                        VoiceGenderPreference.FORCE_MALE -> Emerald100
                        VoiceGenderPreference.FORCE_FEMALE -> Color(0xFFFCE7F3)
                        VoiceGenderPreference.CONTEXT_AWARE -> Gold100
                    },
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "${voicePref.icon} ${voicePref.labelAr}",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = when (voicePref) {
                                VoiceGenderPreference.FORCE_FEMALE -> Color(0xFF9D174D)
                                VoiceGenderPreference.FORCE_MALE -> Emerald800
                                VoiceGenderPreference.CONTEXT_AWARE -> Gold700
                            }
                        )
                        Text(
                            text = "انقر لتبديل الصوت",
                            fontSize = 8.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // Dashboard
                NavigationRailItem(
                    selected = currentScreen == ScreenState.DASHBOARD,
                    onClick = { viewModel.navigateTo(ScreenState.DASHBOARD) },
                    icon = { Icon(Icons.Default.Home, contentDescription = "الرئيسية", modifier = Modifier.size(24.dp)) },
                    label = { Text("الرئيسية", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Emerald800,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // World Map
                NavigationRailItem(
                    selected = currentScreen == ScreenState.WORLD_MAP,
                    onClick = { viewModel.navigateTo(ScreenState.WORLD_MAP) },
                    icon = { Icon(Icons.Default.Public, contentDescription = "العالم العربي", modifier = Modifier.size(24.dp)) },
                    label = { Text("العالم", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Emerald800,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // Vocabulary & Learn
                NavigationRailItem(
                    selected = currentScreen == ScreenState.VOCABULARY,
                    onClick = { viewModel.navigateTo(ScreenState.VOCABULARY) },
                    icon = { Icon(Icons.Default.MenuBook, contentDescription = "التعلم", modifier = Modifier.size(24.dp)) },
                    label = { Text("التعلم", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Emerald800,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )

                // Achievements
                NavigationRailItem(
                    selected = currentScreen == ScreenState.ACHIEVEMENTS,
                    onClick = { viewModel.navigateTo(ScreenState.ACHIEVEMENTS) },
                    icon = { Icon(Icons.Default.CardGiftcard, contentDescription = "الإنجازات", modifier = Modifier.size(24.dp)) },
                    label = { Text("الإنجازات", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Gold700,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )

                if (isTeacher) {
                    // Teacher Dashboard
                    NavigationRailItem(
                        selected = currentScreen == ScreenState.TEACHER_DASHBOARD,
                        onClick = { viewModel.navigateTo(ScreenState.TEACHER_DASHBOARD) },
                        icon = { Icon(Icons.Default.SupervisorAccount, contentDescription = "المعلم", modifier = Modifier.size(24.dp)) },
                        label = { Text("المعلم", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                        colors = NavigationRailItemDefaults.colors(
                            selectedIconColor = Emerald800,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }

                // Profile
                NavigationRailItem(
                    selected = currentScreen == ScreenState.PROFILE,
                    onClick = { viewModel.navigateTo(ScreenState.PROFILE) },
                    icon = { Icon(Icons.Default.Person, contentDescription = "حسابي", modifier = Modifier.size(24.dp)) },
                    label = { Text("حسابي", fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Emerald800,
                        indicatorColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            // Quick Audio Preview Action at bottom of Rail
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp, top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    onClick = {
                        val testPhrase = "مَرْحَباً بِكَ فِي بَرْنَامَجِ مُحَاكَاةِ الحَيَاةِ العَرَبِيَّةِ!"
                        viewModel.audioService.speak(testPhrase, "teacher_female_01")
                    },
                    shape = CircleShape,
                    color = Emerald100,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "اختبار الصوت العربي",
                            tint = Emerald800,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "تجربة الصوت",
                    fontSize = 9.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
