package com.example.ui.screens.mission

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Keyboard
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.speech.SpeechEvaluationResult
import com.example.ui.components.ArabicAudioPlayer
import com.example.ui.components.HintCard
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MissionActivityScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val mission by viewModel.activeMission.collectAsState()
    val activityIndex by viewModel.currentActivityIndex.collectAsState()
    val hintLevel by viewModel.revealedHintLevel.collectAsState()
    val selectedChips by viewModel.selectedSentenceChips.collectAsState()
    val isMicListening by viewModel.speechService.isListening.collectAsState()
    val lastSpeechResult by viewModel.lastSpeechResult.collectAsState()

    BackHandler {
        viewModel.navigateTo(ScreenState.WORLD_MAP)
    }

    if (mission == null) {
        viewModel.navigateTo(ScreenState.WORLD_MAP)
        return
    }

    val currentMission = mission!!
    val currentAct = currentMission.activities.getOrNull(activityIndex)
    if (currentAct == null) {
        viewModel.navigateTo(ScreenState.WORLD_MAP)
        return
    }

    var selectedOption by remember(activityIndex) { mutableStateOf<String?>(null) }
    var typedArabicAnswer by remember(activityIndex) { mutableStateOf("") }
    var useTypingFallback by remember(activityIndex) { mutableStateOf(false) }

    var feedbackMessage by remember(activityIndex) { mutableStateOf<String?>(null) }
    var isAnswerCorrect by remember(activityIndex) { mutableStateOf(false) }
    var isFeedbackVisible by remember(activityIndex) { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("mission_activity_screen")
    ) {
        // Top App Bar
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = { viewModel.navigateTo(ScreenState.WORLD_MAP) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "الرجوع"
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentMission.titleAr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Emerald800
                        )
                        Text(
                            text = "النشاط ${activityIndex + 1} من ${currentMission.activities.size}",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }

                    Surface(
                        color = Gold100,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "+${currentMission.xp} XP",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gold700,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Progress Bar
                LinearProgressIndicator(
                    progress = { (activityIndex + 1).toFloat() / currentMission.activities.size },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = Emerald800,
                    trackColor = Emerald100
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 840.dp)
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
            // Audio Player for contextual listening / spoken line
            if (currentAct.audioTextAr.isNotBlank()) {
                ArabicAudioPlayer(
                    textAr = currentAct.audioTextAr,
                    speakerId = currentAct.speakerId,
                    speakerNameAr = currentAct.speakerNameAr,
                    audioService = viewModel.audioService
                )
            }

            // Prompt Question Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = currentAct.promptAr,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 26.sp
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = currentAct.promptId,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // Interactive Input by Activity Type
            when (currentAct.activityType) {
                // ================= MULTIPLE CHOICE / DECISION BRANCH =================
                "MULTIPLE_CHOICE", "DECISION_BRANCH", "LISTENING" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        currentAct.options.forEach { option ->
                            val isSelected = selectedOption == option
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(14.dp))
                                    .border(
                                        width = if (isSelected) 2.dp else 1.dp,
                                        color = if (isSelected) Emerald800 else MaterialTheme.colorScheme.outlineVariant,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .clickable { selectedOption = option },
                                color = if (isSelected) Emerald100.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(24.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, if (isSelected) Emerald800 else Color.Gray, CircleShape)
                                            .background(if (isSelected) Emerald800 else Color.Transparent),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        if (isSelected) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                tint = Color.White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Text(
                                        text = option,
                                        fontSize = 15.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }
                    }
                }

                // ================= SENTENCE BUILDER =================
                "SENTENCE_BUILDER" -> {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        // Drop Zone (Assembled sentence)
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.5.dp, Emerald700, RoundedCornerShape(14.dp)),
                            color = MaterialTheme.colorScheme.surface
                        ) {
                            if (selectedChips.isEmpty()) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "اضغط على الكلمات بالترتيب لبناء الجملة هنا...",
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                            } else {
                                FlowRow(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(10.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    selectedChips.forEachIndexed { index, chip ->
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable { viewModel.removeSentenceChip(index) },
                                            color = Emerald800
                                        ) {
                                            Text(
                                                text = "$chip ×",
                                                color = Color.White,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Word Chips Pool
                        Text(
                            text = "الكلمات المتاحة (Pilihan Kata):",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            currentAct.wordChips.forEach { chip ->
                                Surface(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp))
                                        .clickable { viewModel.addSentenceChip(chip) },
                                    color = MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = chip,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Medium,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }

                        if (selectedChips.isNotEmpty()) {
                            TextButton(
                                onClick = { viewModel.clearSentenceChips() },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text("إعادة الترتيب (Reset)", fontSize = 12.sp, color = ErrorRed)
                            }
                        }
                    }
                }

                // ================= SPEAKING WITH FALLBACKS =================
                "SPEAKING" -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        if (!useTypingFallback) {
                            // Microphone Button
                            Box(
                                modifier = Modifier
                                    .size(76.dp)
                                    .clip(CircleShape)
                                    .background(if (isMicListening) ErrorRed else Emerald800)
                                    .clickable {
                                        if (isMicListening) {
                                            viewModel.speechService.stopListening()
                                        } else {
                                            viewModel.speechService.startListening(
                                                onResult = { spoken ->
                                                    viewModel.submitSpeech(spoken, currentAct.correctAnswer) { isAcc, eval ->
                                                        isAnswerCorrect = isAcc
                                                        feedbackMessage = if (isAcc) eval.feedbackAr else "حاول مجدداً بنطق أوضح."
                                                        isFeedbackVisible = true
                                                    }
                                                },
                                                onError = { err ->
                                                    feedbackMessage = err
                                                    isFeedbackVisible = true
                                                }
                                            )
                                        }
                                    }
                                    .testTag("mic_button"),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (isMicListening) Icons.Default.MicOff else Icons.Default.Mic,
                                    contentDescription = "الميكروفون",
                                    tint = Color.White,
                                    modifier = Modifier.size(38.dp)
                                )
                            }

                            Text(
                                text = if (isMicListening) "جاري الاستماع... تَكَلَّمْ الآنَ!" else "اضغط على الميكروفون وتحدث باللغة العربية",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = if (isMicListening) ErrorRed else Emerald800
                            )

                            // Display Speech Evaluation Results if available
                            lastSpeechResult?.let { eval ->
                                SpeechResultCard(eval = eval)
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Switch to Typing Fallback button
                            OutlinedButton(
                                onClick = { useTypingFallback = true },
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(Icons.Default.Keyboard, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("التبديل إلى الكتابة العربية (Fallback Typing)", fontSize = 12.sp)
                            }
                        } else {
                            // Typing Fallback View
                            OutlinedTextField(
                                value = typedArabicAnswer,
                                onValueChange = { typedArabicAnswer = it },
                                label = { Text("اكتب الجملة باللغة العربية هنا") },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("arabic_typing_input"),
                                shape = RoundedCornerShape(12.dp)
                            )

                            // Also display quick word chips for convenience
                            if (currentAct.wordChips.isNotEmpty()) {
                                FlowRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    currentAct.wordChips.forEach { chip ->
                                        Surface(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(8.dp))
                                                .clickable {
                                                    typedArabicAnswer = if (typedArabicAnswer.isBlank()) chip else "$typedArabicAnswer $chip"
                                                },
                                            color = MaterialTheme.colorScheme.surfaceVariant
                                        ) {
                                            Text(text = chip, fontSize = 13.sp, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                                        }
                                    }
                                }
                            }

                            TextButton(onClick = { useTypingFallback = false }) {
                                Icon(Icons.Default.Mic, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("الرجوع إلى الميكروفون والصوت", fontSize = 12.sp)
                            }
                        }
                    }
                }
            }

            // Progressive Hint System Card
            HintCard(
                currentHintLevel = hintLevel,
                hint1 = currentAct.hint1,
                hint2 = currentAct.hint2,
                hint3 = currentAct.hint3,
                onRequestHint = { viewModel.requestNextHint() }
            )

            // Feedback Message Banner
            if (isFeedbackVisible && feedbackMessage != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isAnswerCorrect) Emerald100 else Color(0xFFFFE4E6)
                    )
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = if (isAnswerCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                contentDescription = null,
                                tint = if (isAnswerCorrect) SuccessGreen else ErrorRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isAnswerCorrect) "أحسنت! (Tepat sekali)" else "محاولة غير مكتملة (Coba lagi)",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = if (isAnswerCorrect) SuccessGreen else ErrorRed
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = feedbackMessage ?: "",
                            fontSize = 13.sp,
                            lineHeight = 18.sp
                        )

                        if (currentAct.explanationAr.isNotBlank()) {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = currentAct.explanationAr,
                                fontSize = 12.sp,
                                color = Color.DarkGray
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Action Button: Submit or Advance
            if (!isAnswerCorrect) {
                Button(
                    onClick = {
                        val answerToSubmit = when (currentAct.activityType) {
                            "SENTENCE_BUILDER" -> selectedChips.joinToString(" ")
                            "SPEAKING" -> if (useTypingFallback) typedArabicAnswer else (lastSpeechResult?.transcript ?: "")
                            else -> selectedOption ?: ""
                        }

                        if (answerToSubmit.isBlank()) {
                            feedbackMessage = "يرجى اختيار أو تسجيل إجابتك أولاً."
                            isFeedbackVisible = true
                            return@Button
                        }

                        viewModel.submitAnswer(answerToSubmit) { isCorr, fb ->
                            isAnswerCorrect = isCorr
                            feedbackMessage = fb
                            isFeedbackVisible = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("submit_activity_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald800)
                ) {
                    Text(
                        text = "تَحَقَّقْ مِنَ الإِجَابَةِ (Periksa Jawaban)",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                Button(
                    onClick = {
                        viewModel.advanceToNextActivity()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("next_activity_btn"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold700)
                ) {
                    Text(
                        text = if (activityIndex + 1 < currentMission.activities.size) "المرحلة التالية (Berikutnya) ▶" else "إتمام المهمة وحصد النقاط (Selesai) ★",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
}

@Composable
fun SpeechResultCard(eval: SpeechEvaluationResult) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "تقييم النطق والتواصل (Evaluasi Suara):",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Emerald800
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "النطق (Pronunciation): ${eval.pronunciationScore}%", fontSize = 11.sp)
                Text(text = "الطلاقة (Fluency): ${eval.fluencyScore}%", fontSize = 11.sp)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "القواعد (Grammar): ${eval.grammarScore}%", fontSize = 11.sp)
                Text(text = "المفردات (Vocab): ${eval.vocabularyScore}%", fontSize = 11.sp)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "الدرجة الإجمالية: ${eval.overallScore}%",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if (eval.isAcceptable) SuccessGreen else Gold700
            )
        }
    }
}
