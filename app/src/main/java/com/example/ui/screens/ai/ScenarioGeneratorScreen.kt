package com.example.ui.screens.ai

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ai.model.ScenarioDifficulty
import com.example.ai.model.ScenarioTheme
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.viewmodel.ArabicScenarioGeneratorViewModel
import com.example.viewmodel.ScenarioGenerationUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScenarioGeneratorScreen(
    viewModel: ArabicScenarioGeneratorViewModel,
    onBackToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val selectedTheme by viewModel.selectedTheme.collectAsState()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsState()
    val customDetail by viewModel.customSituationDetail.collectAsState()
    val characterGender by viewModel.characterGenderPreference.collectAsState()

    BackHandler {
        if (uiState is ScenarioGenerationUiState.Active) {
            viewModel.resetToThemeSelection()
        } else {
            onBackToHome()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("scenario_generator_screen")
    ) {
        // Top App Bar
        Surface(
            color = Emerald800,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    if (uiState is ScenarioGenerationUiState.Active) {
                        viewModel.resetToThemeSelection()
                    } else {
                        onBackToHome()
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "الرجوع",
                        tint = Color.White
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = Gold100,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "توليد المحاكاة بالذكاء الاصطناعي",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "مدعوم بـ Firebase AI SDK (Gemini)",
                            fontSize = 11.sp,
                            color = Emerald100
                        )
                    }
                }
            }
        }

        when (val state = uiState) {
            is ScenarioGenerationUiState.Idle -> {
                // Theme Selection & Configuration Screen
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Text(
                            text = "اختر موضوع المحاكاة الحياتية (Pilih Tema Simulasi):",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    // Grid of Themes
                    items(ScenarioTheme.ALL_THEMES) { theme ->
                        val isSelected = selectedTheme.id == theme.id
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(16.dp))
                                .border(
                                    width = if (isSelected) 2.dp else 1.dp,
                                    color = if (isSelected) Emerald800 else MaterialTheme.colorScheme.outlineVariant,
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clickable { viewModel.selectTheme(theme) }
                                .testTag("theme_card_${theme.id}"),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) Emerald100.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) Emerald800 else Emerald100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = theme.icon, fontSize = 24.sp)
                                }

                                Spacer(modifier = Modifier.width(14.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = theme.nameAr,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "(${theme.nameId})",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = theme.description,
                                        fontSize = 12.sp,
                                        color = Color.Gray,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    // Difficulty Selection
                    item {
                        Text(
                            text = "مستوى الصعوبة (Tingkat Kesulitan):",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ScenarioDifficulty.entries.forEach { diff ->
                                val isSel = selectedDifficulty == diff
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { viewModel.selectDifficulty(diff) },
                                    color = if (isSel) Gold700 else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Column(
                                        modifier = Modifier.padding(vertical = 10.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Text(
                                            text = diff.labelAr,
                                            fontSize = 13.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = diff.levelCode,
                                            fontSize = 10.sp,
                                            color = if (isSel) Gold100 else Color.Gray
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Character Gender Selection (Context Audio)
                    item {
                        Text(
                            text = "صوت وجنس الشخصية (Karakter & Suara Penutur):",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "اختر نبرة الصوت العربي المميزة (رجالي أو نسائي)",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val genders = listOf(
                                Triple("AUTO", "تلقائي", "🎭"),
                                Triple("MALE", "رجالي (فهد)", "🧔"),
                                Triple("FEMALE", "نسائي (مريم)", "🧕")
                            )
                            genders.forEach { (code, label, icon) ->
                                val isSel = characterGender == code
                                Surface(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { viewModel.setCharacterGender(code) },
                                    color = if (isSel) {
                                        if (code == "FEMALE") Color(0xFFBE185D) else Emerald800
                                    } else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Row(
                                        modifier = Modifier.padding(vertical = 10.dp, horizontal = 4.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = icon, fontSize = 16.sp)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = label,
                                            fontSize = 12.sp,
                                            fontWeight = if (isSel) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSel) Color.White else MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Optional Custom Scenario Details
                    item {
                        Text(
                            text = "تفاصيل إضافية مخصصة (Opsional):",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        OutlinedTextField(
                            value = customDetail,
                            onValueChange = { viewModel.setCustomDetail(it) },
                            placeholder = { Text("مثال: أريد موقفاً عن ضياع حقيبتي عند استلام الأمتعة...") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    // Generate Action Button
                    item {
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            onClick = { viewModel.generateScenarioWithFirebaseAi() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("generate_scenario_btn"),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Gold100)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "✨ توليد محاكاة جديدة (Generate Scenario)",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

            is ScenarioGenerationUiState.Generating -> {
                // Loading / Shimmer State
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        CircularProgressIndicator(color = Emerald800, modifier = Modifier.size(52.dp))
                        Text(
                            text = "جاري إنشاء الموقف الحياتي بالذكاء الاصطناعي...",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Emerald800,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = state.statusMessage,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            is ScenarioGenerationUiState.Active -> {
                // Active Interactive Scenario Execution
                var userResponseText by remember(state.currentStageIndex) { mutableStateOf("") }
                val currentStage = state.scenario.stages.getOrNull(state.currentStageIndex)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // Scenario Header Card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = Emerald100,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = state.scenario.locationNameAr,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald800,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }

                                Text(
                                    text = "المرحلة ${state.currentStageIndex + 1} من ${state.scenario.stages.size}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Gold700
                                )
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = state.scenario.titleAr,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = state.scenario.situationBackgroundAr,
                                fontSize = 12.sp,
                                color = Color.Gray,
                                lineHeight = 16.sp
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            LinearProgressIndicator(
                                progress = { (state.currentStageIndex + 1).toFloat() / state.scenario.stages.size },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(2.dp)),
                                color = Emerald800,
                                trackColor = Emerald100
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Conversation / Dialogue Stream
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(state.conversationHistory) { turn ->
                            val isAi = turn.speakerRole == "AI"
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
                            ) {
                                Card(
                                    shape = RoundedCornerShape(
                                        topStart = 14.dp,
                                        topEnd = 14.dp,
                                        bottomStart = if (isAi) 2.dp else 14.dp,
                                        bottomEnd = if (isAi) 14.dp else 2.dp
                                    ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isAi) MaterialTheme.colorScheme.surface else Emerald800
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                    modifier = Modifier.fillMaxWidth(0.85f)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = turn.speakerNameAr,
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isAi) Gold700 else Emerald100
                                            )
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                // Male audio button
                                                Surface(
                                                    onClick = { viewModel.playAudio(turn.textAr, forceFemale = false) },
                                                    shape = CircleShape,
                                                    color = Emerald100,
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Text(text = "🧔", fontSize = 13.sp)
                                                    }
                                                }

                                                // Female audio button
                                                Surface(
                                                    onClick = { viewModel.playAudio(turn.textAr, forceFemale = true) },
                                                    shape = CircleShape,
                                                    color = Color(0xFFFCE7F3),
                                                    modifier = Modifier.size(28.dp)
                                                ) {
                                                    Box(contentAlignment = Alignment.Center) {
                                                        Text(text = "🧕", fontSize = 13.sp)
                                                    }
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = turn.textAr,
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            color = if (isAi) MaterialTheme.colorScheme.onSurface else Color.White
                                        )

                                        if (turn.textId.isNotBlank()) {
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = turn.textId,
                                                fontSize = 11.sp,
                                                color = if (isAi) Color.Gray else Emerald100.copy(alpha = 0.8f)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Target Goal Card for Current Stage
                        currentStage?.let { stage ->
                            item {
                                Surface(
                                    color = Gold100,
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(modifier = Modifier.padding(10.dp)) {
                                        Text(
                                            text = "🎯 هدفك في هذا الموقف (Tujuan Anda):",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = Gold700
                                        )
                                        Text(
                                            text = stage.userGoalDescription,
                                            fontSize = 12.sp,
                                            color = Color.DarkGray
                                        )
                                    }
                                }
                            }
                        }

                        // Stage Feedback if evaluated
                        if (state.isStageEvaluated && state.feedbackAr != null) {
                            item {
                                Surface(
                                    color = SuccessGreen.copy(alpha = 0.15f),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier.padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = SuccessGreen)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = state.feedbackAr,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = SuccessGreen
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Suggested Quick Response Chips
                    currentStage?.suggestedResponsesAr?.let { responses ->
                        if (!state.isStageEvaluated) {
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                items(responses) { resp ->
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { userResponseText = resp },
                                        color = Emerald100
                                    ) {
                                        Text(
                                            text = resp,
                                            fontSize = 12.sp,
                                            color = Emerald800,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                        }
                    }

                    // Input Row or Next Stage Action
                    if (!state.isStageEvaluated) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = userResponseText,
                                onValueChange = { userResponseText = it },
                                placeholder = { Text("أجب بالعربية هنا...") },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("scenario_user_input"),
                                shape = RoundedCornerShape(14.dp)
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            IconButton(
                                onClick = {
                                    if (userResponseText.isNotBlank()) {
                                        viewModel.submitUserTurn(userResponseText)
                                    }
                                },
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(CircleShape)
                                    .background(Emerald800)
                                    .testTag("scenario_send_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "إرسال",
                                    tint = Color.White
                                )
                            }
                        }
                    } else {
                        Button(
                            onClick = { viewModel.advanceToNextStage() },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("scenario_next_stage_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Gold700)
                        ) {
                            Text(
                                text = if (state.currentStageIndex + 1 < state.scenario.stages.size) "المرحلة التالية (Berikutnya) ▶" else "إنهاء المحاكاة وحصد النقاط ★",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            is ScenarioGenerationUiState.Completed -> {
                // Celebration & Debrief
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Gold100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎉", fontSize = 36.sp)
                    }

                    Text(
                        text = "مُبَارَكٌ! أَتْمَمْتَ المَوْقِفَ بِنَجَاحٍ!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald800
                    )

                    Text(
                        text = state.scenario.titleAr,
                        fontSize = 15.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "📚 المفردات المستفادة في هذا الموقف:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Spacer(modifier = Modifier.height(6.dp))
                            state.scenario.targetVocabulary.forEach { (word, meaning) ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(text = word, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Emerald800)
                                    Text(text = meaning, fontSize = 12.sp, color = Color.Gray)
                                }
                            }

                            Divider(modifier = Modifier.padding(vertical = 10.dp))

                            Text(text = "💡 الفائدة الثقافية واللباقة:", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Gold700)
                            Text(text = state.scenario.culturalEtiquetteTip, fontSize = 12.sp)
                        }
                    }

                    Button(
                        onClick = { viewModel.resetToThemeSelection() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                    ) {
                        Icon(Icons.Default.Replay, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("توليد سيناريو آخر (Generate Another)")
                    }

                    TextButton(onClick = onBackToHome) {
                        Text("الرجوع للرئيسية (Ke Dashboard)", color = Emerald800)
                    }
                }
            }

            is ScenarioGenerationUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = "⚠️", fontSize = 36.sp)
                        Text(
                            text = state.errorMessage,
                            color = ErrorRed,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewModel.resetToThemeSelection() },
                            colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                        ) {
                            Text("الرجوع للاختيار")
                        }
                    }
                }
            }
        }
    }
}
