package com.example.ui.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioService
import com.example.audio.VoiceGenderPreference
import com.example.data.seed.SeedFinalChallenge
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold500
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.Slate900
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun StudentDashboardScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val profile by viewModel.currentProfile.collectAsState()
    val completedIds by viewModel.completedMissionIds.collectAsState()
    val allMissions = viewModel.repository.allMissions

    // Determine Next Recommended Mission
    val nextMission = allMissions.find { !completedIds.contains(it.id) } ?: allMissions.first()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("student_dashboard_screen")
    ) {
        val isLaptopLayout = maxWidth >= 720.dp

        if (isLaptopLayout) {
            // Laptop / Large Screen: Dual Column Dashboard Layout
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Left Column: User Profile & Voice Studio & Skills (width: 360.dp)
                Column(
                    modifier = Modifier
                        .width(360.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    WelcomeHeader(userName = user?.name, avatar = profile?.avatar) {
                        viewModel.navigateTo(ScreenState.PROFILE)
                    }

                    StatsBanner(
                        streak = profile?.streak ?: 1,
                        xp = profile?.xp ?: 0,
                        level = profile?.currentLevel ?: 1
                    )

                    ArabicVoiceStudioCard(audioService = viewModel.audioService)

                    SkillsCard(
                        listening = profile?.listeningMastery ?: 70,
                        speaking = profile?.speakingMastery ?: 60,
                        reading = profile?.readingMastery ?: 80,
                        writing = profile?.writingMastery ?: 65,
                        vocabulary = profile?.vocabularyMastery ?: 75,
                        grammar = profile?.grammarMastery ?: 68
                    )
                }

                // Right Column: Recommended Mission & Quick Actions & Final Challenge
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    NextMissionHeroCard(
                        titleAr = nextMission.titleAr,
                        objective = nextMission.objective,
                        xp = nextMission.xp,
                        onContinue = { viewModel.startMission(nextMission) }
                    )

                    QuickActionsSection(viewModel = viewModel)

                    FinalChallengeBanner {
                        viewModel.startMission(SeedFinalChallenge.finalChallengeMission)
                    }
                }
            }
        } else {
            // Handphone / Compact Layout: Single Scrollable Column
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                WelcomeHeader(userName = user?.name, avatar = profile?.avatar) {
                    viewModel.navigateTo(ScreenState.PROFILE)
                }

                StatsBanner(
                    streak = profile?.streak ?: 1,
                    xp = profile?.xp ?: 0,
                    level = profile?.currentLevel ?: 1
                )

                NextMissionHeroCard(
                    titleAr = nextMission.titleAr,
                    objective = nextMission.objective,
                    xp = nextMission.xp,
                    onContinue = { viewModel.startMission(nextMission) }
                )

                // Voice & Pronunciation Studio Widget
                ArabicVoiceStudioCard(audioService = viewModel.audioService)

                QuickActionsSection(viewModel = viewModel)

                FinalChallengeBanner {
                    viewModel.startMission(SeedFinalChallenge.finalChallengeMission)
                }

                SkillsCard(
                    listening = profile?.listeningMastery ?: 70,
                    speaking = profile?.speakingMastery ?: 60,
                    reading = profile?.readingMastery ?: 80,
                    writing = profile?.writingMastery ?: 65,
                    vocabulary = profile?.vocabularyMastery ?: 75,
                    grammar = profile?.grammarMastery ?: 68
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun WelcomeHeader(
    userName: String?,
    avatar: String?,
    onProfileClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "مَرْحَبًا، ${userName ?: "يا متعلم"}",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "عِشِ المَوْقِفَ، تَكَلَّمْ بِالعَرَبِيَّةِ",
                fontSize = 13.sp,
                color = Emerald700,
                fontWeight = FontWeight.Medium
            )
        }

        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Emerald100)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = avatar ?: "🧑‍🎓", fontSize = 24.sp)
        }
    }
}

@Composable
private fun StatsBanner(
    streak: Int,
    xp: Int,
    level: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Streak
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = WarningOrange,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$streak",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = WarningOrange
                    )
                }
                Text(text = "يوم تواصل (Streak)", fontSize = 11.sp, color = Color.Gray)
            }

            // XP
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = null,
                        tint = Gold600,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$xp",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Gold600
                    )
                }
                Text(text = "نقطة (XP)", fontSize = 11.sp, color = Color.Gray)
            }

            // Level
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Emerald700,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "المستوى $level",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Emerald700
                    )
                }
                Text(text = "الحياة اليومية", fontSize = 11.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun ArabicVoiceStudioCard(
    audioService: AudioService,
    modifier: Modifier = Modifier
) {
    val voicePref by audioService.voicePreference.collectAsState()

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Emerald100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎙️", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "استوديو النطق العربي الأصيل",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Emerald800
                        )
                        Text(
                            text = "تبديل الصوت حسب السياق (رجالي / نسائي)",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (voicePref) {
                        VoiceGenderPreference.FORCE_MALE -> Emerald100
                        VoiceGenderPreference.FORCE_FEMALE -> Color(0xFFFCE7F3)
                        VoiceGenderPreference.CONTEXT_AWARE -> Gold100
                    }
                ) {
                    Text(
                        text = voicePref.icon,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Three Selection Chips for Voice
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val options = listOf(
                    Triple(VoiceGenderPreference.CONTEXT_AWARE, "تلقائي بالسياق", "🎭"),
                    Triple(VoiceGenderPreference.FORCE_MALE, "رجالي (أحمد)", "🧔"),
                    Triple(VoiceGenderPreference.FORCE_FEMALE, "نسائي (فاطمة)", "🧕")
                )
                options.forEach { (pref, label, icon) ->
                    val isSelected = voicePref == pref
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { audioService.setVoicePreference(pref) },
                        color = if (isSelected) {
                            if (pref == VoiceGenderPreference.FORCE_FEMALE) Color(0xFFBE185D) else Emerald800
                        } else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(text = icon, fontSize = 13.sp)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Test phrase buttons with authentic Arabic articulation
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Play Male preview
                Surface(
                    onClick = {
                        audioService.speakAsMale("السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللهِ، أَهْلًا وَسَهْلًا بِكَ!")
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = Emerald100,
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "🧔", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "نطق رجالي",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Emerald800
                        )
                    }
                }

                // Play Female preview
                Surface(
                    onClick = {
                        audioService.speakAsFemale("السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللهِ، أَهْلًا وَسَهْلًا بِكِ!")
                    },
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFFCE7F3),
                    modifier = Modifier.weight(1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "🧕", fontSize = 14.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "نطق نسائي",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF9D174D)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NextMissionHeroCard(
    titleAr: String,
    objective: String,
    xp: Int,
    onContinue: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("next_mission_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Emerald800),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "المهمة التالية (Misi Berikutnya)",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Text(
                    text = "+$xp XP",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Gold500
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = titleAr,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = objective,
                fontSize = 13.sp,
                color = Emerald100,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onContinue() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("continue_mission_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Gold600)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "▶ مواصلة المهمة (Lanjutkan Misi)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun QuickActionsSection(viewModel: MainViewModel) {
    Column {
        Text(
            text = "المحطات والتفاعل (Pusat Pembelajaran):",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // World Map Button
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.navigateTo(ScreenState.WORLD_MAP) }
                    .testTag("open_world_map_btn"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "🌍", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "العالم العربي", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "21 موقعاً حيوياً", fontSize = 11.sp, color = Color.Gray)
                }
            }

            // AI Roleplay Button
            Card(
                modifier = Modifier
                    .weight(1f)
                    .clickable { viewModel.startRoleplay(viewModel.roleplayEngine.scenarios.first()) }
                    .testTag("open_roleplay_btn"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = "🎭", fontSize = 28.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "محاكاة المحادثة", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text(text = "حوارات ذكية مباشرة", fontSize = 11.sp, color = Color.Gray)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Firebase AI Scenario Generator Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.navigateTo(ScreenState.AI_SCENARIO_GENERATOR) }
                .testTag("open_ai_generator_btn"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Emerald100)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "✨", fontSize = 28.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "توليد محاكاة بالذكاء الاصطناعي (Firebase AI)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Emerald800
                        )
                        Text(
                            text = "سيناريوهات ومواقف حية بأصوات عربية مميزة",
                            fontSize = 11.sp,
                            color = Slate900.copy(alpha = 0.7f)
                        )
                    }
                }
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Emerald800)
            }
        }
    }
}

@Composable
private fun FinalChallengeBanner(onOpen: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .testTag("open_final_challenge_btn"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Gold100)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "🌟", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "التحدي الكبير: يوم كامل باللغة العربية",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Gold700
                    )
                    Text(
                        text = "محاكاة شاملة بأحداث طارئة متفرعة",
                        fontSize = 11.sp,
                        color = Slate900.copy(alpha = 0.7f)
                    )
                }
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Gold700)
        }
    }
}

@Composable
private fun SkillsCard(
    listening: Int,
    speaking: Int,
    reading: Int,
    writing: Int,
    vocabulary: Int,
    grammar: Int
) {
    Column {
        Text(
            text = "المهارات اللغوية الست (6 Keterampilan Bahasa):",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                SkillProgressRow(title = "الاستماع (Listening)", score = listening, color = Emerald800)
                Spacer(modifier = Modifier.height(10.dp))
                SkillProgressRow(title = "الكلام (Speaking)", score = speaking, color = Gold600)
                Spacer(modifier = Modifier.height(10.dp))
                SkillProgressRow(title = "القراءة (Reading)", score = reading, color = Emerald700)
                Spacer(modifier = Modifier.height(10.dp))
                SkillProgressRow(title = "الكتابة (Writing)", score = writing, color = Color(0xFF0284C7))
                Spacer(modifier = Modifier.height(10.dp))
                SkillProgressRow(title = "المفردات (Vocabulary)", score = vocabulary, color = Color(0xFF8B5CF6))
                Spacer(modifier = Modifier.height(10.dp))
                SkillProgressRow(title = "النحو (Grammar)", score = grammar, color = Color(0xFFEC4899))
            }
        }
    }
}

@Composable
fun SkillProgressRow(title: String, score: Int, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(text = "$score%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = { (score / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}
