package com.example.ui.screens.result

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun MissionResultScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val result by viewModel.lastMissionResult.collectAsState()
    val mission by viewModel.activeMission.collectAsState()

    BackHandler {
        viewModel.navigateTo(ScreenState.WORLD_MAP)
    }

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
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Success Trophy Icon
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Gold100),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🏆", fontSize = 42.sp)
        }

        Text(
            text = "أَحْسَنْتَ صُنْعًا!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Emerald800
        )

        Text(
            text = "أكملت مهمة: ${mission?.titleAr ?: "المهمة بنجاح"}",
            fontSize = 15.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        // XP & Score Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ElectricBolt, contentDescription = null, tint = Gold600, modifier = Modifier.size(24.dp))
                        Text(
                            text = "+${result?.xpEarned ?: 50}",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gold600
                        )
                    }
                    Text(text = "نقاط الخبرة (XP)", fontSize = 12.sp, color = Color.Gray)
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Star, contentDescription = null, tint = Emerald800, modifier = Modifier.size(24.dp))
                        Text(
                            text = "${result?.score ?: 95}%",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald800
                        )
                    }
                    Text(text = "درجة الدقة (Skor)", fontSize = 12.sp, color = Color.Gray)
                }
            }
        }

        // 6 Maharah Skills Breakdown
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "تقييم المهارات الست في هذه التجربة:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Emerald800
                )

                Spacer(modifier = Modifier.height(12.dp))

                ResultSkillBar(label = "الاستماع (Listening)", score = result?.listeningScore ?: 90, color = Emerald800)
                Spacer(modifier = Modifier.height(8.dp))
                ResultSkillBar(label = "الكلام (Speaking)", score = result?.speakingScore ?: 85, color = Gold600)
                Spacer(modifier = Modifier.height(8.dp))
                ResultSkillBar(label = "القراءة (Reading)", score = result?.readingScore ?: 95, color = Emerald800)
                Spacer(modifier = Modifier.height(8.dp))
                ResultSkillBar(label = "الكتابة (Writing)", score = result?.writingScore ?: 88, color = Color(0xFF0284C7))
                Spacer(modifier = Modifier.height(8.dp))
                ResultSkillBar(label = "المفردات (Vocabulary)", score = result?.vocabularyScore ?: 92, color = Color(0xFF8B5CF6))
                Spacer(modifier = Modifier.height(8.dp))
                ResultSkillBar(label = "النحو (Grammar)", score = result?.grammarScore ?: 89, color = Color(0xFFEC4899))
            }
        }

        // Strengths & Recommendations Card (Section 34)
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Emerald100.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "💪", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "نقاط القوة (Kekuatan): المفردات ${result?.vocabularyScore ?: 92}%",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = Emerald800
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🎯", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "تحتاج إلى التدريب: الاستماع والتحدث في المواقف السريعة",
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = WarningOrange
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "نصيحة المعلم: جرّب تكرار الاستماع بدون إظهار النص العربي لتقوية الأذن اللغوية.",
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Navigation Actions
        Button(
            onClick = { viewModel.navigateTo(ScreenState.WORLD_MAP) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .testTag("result_continue_btn"),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
        ) {
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "متابعة الاستكشاف في الخريطة (Peta Dunia)", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = { viewModel.navigateTo(ScreenState.DASHBOARD) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "الرجوع للرئيسية (Ke Dashboard)", color = Emerald800)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
}

@Composable
fun ResultSkillBar(label: String, score: Int, color: Color) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = label, fontSize = 12.sp)
            Text(text = "$score%", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
        }
        Spacer(modifier = Modifier.height(3.dp))
        LinearProgressIndicator(
            progress = { (score / 100f).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
                .clip(RoundedCornerShape(3.dp)),
            color = color,
            trackColor = color.copy(alpha = 0.2f)
        )
    }
}
