package com.example.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.ui.screens.dashboard.ArabicVoiceStudioCard
import com.example.ui.screens.dashboard.SkillProgressRow
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.MainViewModel

@Composable
fun ProfileScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val user by viewModel.currentUser.collectAsState()
    val profile by viewModel.currentProfile.collectAsState()
    val completedIds by viewModel.completedMissionIds.collectAsState()
    val earnedIds by viewModel.earnedAchievementIds.collectAsState()

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 680.dp)
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(16.dp)
                .testTag("profile_screen"),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Avatar & Name Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(Emerald100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = profile?.avatar ?: "🧑‍🎓", fontSize = 42.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = user?.name ?: "المتعلم الذكي",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = user?.email ?: "student@arabiyyun.com",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = Emerald100,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = if (user?.role == "TEACHER") "👨‍🏫 حساب معلم (Teacher)" else "🧑‍🎓 حساب طالب (Student)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald800,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // Stats Overview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${profile?.xp ?: 0}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Gold600)
                        Text(text = "إجمالي النقاط (XP)", fontSize = 11.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${profile?.streak ?: 1} 🔥", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = WarningOrange)
                        Text(text = "أيام التواصل", fontSize = 11.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${completedIds.size}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Emerald800)
                        Text(text = "مهمات منجزة", fontSize = 11.sp, color = Color.Gray)
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "${earnedIds.size}", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Gold700)
                        Text(text = "أوسمة فخرية", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            // Arabic Voice Studio Card in Profile
            ArabicVoiceStudioCard(audioService = viewModel.audioService)

            // 6 Maharah Skills Progress (المهارات اللغوية الست)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "كفاءة المهارات اللغوية (Kemahiran Bahasa):",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald800
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    SkillProgressRow(title = "الاستماع (Listening)", score = profile?.listeningMastery ?: 70, color = Emerald800)
                    Spacer(modifier = Modifier.height(8.dp))
                    SkillProgressRow(title = "الكلام (Speaking)", score = profile?.speakingMastery ?: 65, color = Gold600)
                    Spacer(modifier = Modifier.height(8.dp))
                    SkillProgressRow(title = "القراءة (Reading)", score = profile?.readingMastery ?: 80, color = Emerald700)
                    Spacer(modifier = Modifier.height(8.dp))
                    SkillProgressRow(title = "الكتابة (Writing)", score = profile?.writingMastery ?: 70, color = Color(0xFF0284C7))
                    Spacer(modifier = Modifier.height(8.dp))
                    SkillProgressRow(title = "المفردات (Vocabulary)", score = profile?.vocabularyMastery ?: 75, color = Color(0xFF8B5CF6))
                    Spacer(modifier = Modifier.height(8.dp))
                    SkillProgressRow(title = "النحو (Grammar)", score = profile?.grammarMastery ?: 68, color = Color(0xFFEC4899))
                }
            }

            // Logout Button
            OutlinedButton(
                onClick = { viewModel.logout() },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("logout_btn"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorRed)
            ) {
                Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "تسجيل الخروج (Keluar Akun)", fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
