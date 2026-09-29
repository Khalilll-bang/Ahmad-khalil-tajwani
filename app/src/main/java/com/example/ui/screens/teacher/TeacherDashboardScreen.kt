package com.example.ui.screens.teacher

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserEntity
import com.example.data.model.UserProfileEntity
import com.example.ui.screens.dashboard.SkillProgressRow
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.MainViewModel

@Composable
fun TeacherDashboardScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val students by viewModel.studentList.collectAsState()
    val profiles by viewModel.allProfiles.collectAsState()
    val analytics = viewModel.getTeacherAnalytics()

    var inspectedStudent by remember { mutableStateOf<UserEntity?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .widthIn(max = 840.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("teacher_dashboard_screen"),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
        // Teacher Welcome Header
        item {
            Surface(
                color = Emerald800,
                shape = RoundedCornerShape(18.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🧑‍🏫", fontSize = 24.sp)
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "لوحة تحكم المعلم (Teacher Analytics)",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "متابعة أداء الطلاب والمهارات الست والتعلم التكيفي",
                                fontSize = 12.sp,
                                color = Emerald100
                            )
                        }
                    }
                }
            }
        }

        // Summary Analytics Grid
        item {
            Text(
                text = "الإحصاءات العامة للمنصة (Statistik Pembelajaran):",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AnalyticsStatCard(
                    title = "إجمالي الطلاب",
                    value = "${students.size}",
                    subtitle = "طالب نشط",
                    icon = "👥",
                    modifier = Modifier.weight(1f)
                )

                AnalyticsStatCard(
                    title = "متوسط النقاط",
                    value = "${analytics.averageXp}",
                    subtitle = "نقطة XP",
                    icon = "⚡",
                    modifier = Modifier.weight(1f)
                )

                AnalyticsStatCard(
                    title = "نسبة الإنجاز",
                    value = "${analytics.averageProgressPercent}%",
                    subtitle = "تقدم المنهج",
                    icon = "📊",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Weak Points & Adaptive Focus
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Gold100)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Warning, contentDescription = null, tint = WarningOrange)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "تحليل الصعوبات والتعلم التكيفي (Analisis Kesulitan):",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = Gold700
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "• المهارة الأكثر حاجة للدعم: ${analytics.weakestSkill}", fontSize = 12.sp)
                    Text(text = "• أكثر مهمة واجهت تحديات: ${analytics.mostChallengingMission}", fontSize = 12.sp)
                    Text(text = "• المفردات الأكثر خطأً: ${analytics.mostMissedVocab}", fontSize = 12.sp)
                }
            }
        }

        // Student Table Header
        item {
            Text(
                text = "قائمة الطلاب وتقارير المهارات (Daftar Siswa):",
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }

        // Students Items
        items(students) { student ->
            val profile = profiles.find { it.userId == student.id }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { inspectedStudent = student }
                    .testTag("student_row_${student.id}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(Emerald100),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = profile?.avatar ?: "🧑‍🎓", fontSize = 20.sp)
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(text = student.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                            Text(
                                text = "${student.email} • المستوى ${profile?.currentLevel ?: 1}",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "${profile?.xp ?: 0} XP",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = Gold700
                            )
                            Text(
                                text = "الاستماع: ${profile?.listeningMastery ?: 60}%",
                                fontSize = 10.sp,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
                    }
                }
            }
        }
    }
}

    // Student Detail Inspector Dialog
    inspectedStudent?.let { st ->
        val prof = profiles.find { it.userId == st.id }

        AlertDialog(
            onDismissRequest = { inspectedStudent = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = prof?.avatar ?: "🧑‍🎓", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = st.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "تقرير كفاءة المهارات اللغوية", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "مجموع النقاط: ${prof?.xp ?: 0} XP | التواصل: ${prof?.streak ?: 1} أيام", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Divider()
                    SkillProgressRow(title = "الاستماع", score = prof?.listeningMastery ?: 70, color = Emerald800)
                    SkillProgressRow(title = "الكلام", score = prof?.speakingMastery ?: 65, color = Gold700)
                    SkillProgressRow(title = "القراءة", score = prof?.readingMastery ?: 80, color = Emerald800)
                    SkillProgressRow(title = "الكتابة", score = prof?.writingMastery ?: 70, color = Color(0xFF0284C7))
                    SkillProgressRow(title = "المفردات", score = prof?.vocabularyMastery ?: 75, color = Color(0xFF8B5CF6))
                    SkillProgressRow(title = "النحو", score = prof?.grammarMastery ?: 68, color = Color(0xFFEC4899))
                }
            },
            confirmButton = {
                Button(
                    onClick = { inspectedStudent = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                ) {
                    Text("إغلاق (Tutup)")
                }
            }
        )
    }
}

@Composable
fun AnalyticsStatCard(
    title: String,
    value: String,
    subtitle: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Emerald800)
            Text(text = title, fontSize = 10.sp, color = Color.Gray)
            Text(text = subtitle, fontSize = 9.sp, color = Color.Gray)
        }
    }
}
