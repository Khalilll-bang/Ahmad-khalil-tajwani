package com.example.ui.screens.roleplay

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.ScreenState

@Composable
fun RoleplayScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scenario by viewModel.activeScenario.collectAsState()
    val chatHistory by viewModel.roleplayChatHistory.collectAsState()

    var inputMessage by remember { mutableStateOf("") }

    BackHandler {
        viewModel.navigateTo(ScreenState.DASHBOARD)
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("roleplay_screen")
    ) {
        val isLaptopLayout = maxWidth >= 720.dp

        Column(modifier = Modifier.fillMaxSize()) {
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
                    IconButton(onClick = { viewModel.navigateTo(ScreenState.DASHBOARD) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "الرجوع",
                            tint = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🎭", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = scenario.characterNameAr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                        Text(
                            text = "${scenario.characterRoleAr} • ${scenario.locationNameAr}",
                            fontSize = 11.sp,
                            color = Emerald100
                        )
                    }
                }
            }

            if (isLaptopLayout) {
                // Laptop Layout: 2-Column Split
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Left Column: Character Dossier & Voice Studio (width ~ 340.dp)
                    Card(
                        modifier = Modifier
                            .width(340.dp)
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(18.dp)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(Emerald100),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "🧑‍💼", fontSize = 26.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = scenario.characterNameAr,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Emerald800
                                    )
                                    Text(
                                        text = scenario.characterRoleAr,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Divider()

                            Text(
                                text = "الموقع: ${scenario.locationNameAr}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = scenario.promptContext,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = Color.Gray
                            )

                            Text(
                                text = "أهداف المحاكاة: ${scenario.targetGoals.joinToString(" • ")}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Gold700
                            )

                            Divider()

                            // Voice tester for this scenario
                            Text(
                                text = "اختبار الصوت المميز:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Emerald800
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Surface(
                                    onClick = { viewModel.audioService.speakAsMale(scenario.initialAiGreetingAr) },
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
                                        Text(text = "صوت رجالي", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Emerald800)
                                    }
                                }

                                Surface(
                                    onClick = { viewModel.audioService.speakAsFemale(scenario.initialAiGreetingAr) },
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
                                        Text(text = "صوت نسائي", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF9D174D))
                                    }
                                }
                            }

                            Divider()

                            Text(
                                text = "تبديل الموقف الحواري:",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            viewModel.roleplayEngine.scenarios.forEach { sc ->
                                val isSelected = sc.scenarioId == scenario.scenarioId
                                Surface(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(10.dp))
                                        .clickable { viewModel.startRoleplay(sc) },
                                    color = if (isSelected) Emerald800 else MaterialTheme.colorScheme.surfaceVariant
                                ) {
                                    Text(
                                        text = "${sc.characterNameAr} (${sc.locationNameAr})",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                    // Right Column: Chat messages & Response Composer (weight 1f)
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        ChatMessagesList(
                            chatHistory = chatHistory,
                            viewModel = viewModel,
                            scenarioCharacterId = scenario.characterId,
                            modifier = Modifier.weight(1f)
                        )

                        QuickSuggestionsRow(
                            scenarioId = scenario.scenarioId,
                            onSelect = { inputMessage = it }
                        )

                        ChatInputBar(
                            inputMessage = inputMessage,
                            onMessageChange = { inputMessage = it },
                            onSend = {
                                if (inputMessage.isNotBlank()) {
                                    viewModel.sendRoleplayMessage(inputMessage)
                                    inputMessage = ""
                                }
                            }
                        )
                    }
                }
            } else {
                // Mobile Layout: Single Column
                Column(modifier = Modifier.fillMaxSize()) {
                    // Scenario Selector Bar
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(vertical = 8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(viewModel.roleplayEngine.scenarios) { sc ->
                            val isSelected = sc.scenarioId == scenario.scenarioId
                            Surface(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .clickable { viewModel.startRoleplay(sc) },
                                color = if (isSelected) Gold700 else MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = "${sc.characterNameAr} (${sc.locationNameAr})",
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                )
                            }
                        }
                    }

                    // Chat Messages Stream
                    ChatMessagesList(
                        chatHistory = chatHistory,
                        viewModel = viewModel,
                        scenarioCharacterId = scenario.characterId,
                        modifier = Modifier.weight(1f)
                    )

                    QuickSuggestionsRow(
                        scenarioId = scenario.scenarioId,
                        onSelect = { inputMessage = it }
                    )

                    ChatInputBar(
                        inputMessage = inputMessage,
                        onMessageChange = { inputMessage = it },
                        onSend = {
                            if (inputMessage.isNotBlank()) {
                                viewModel.sendRoleplayMessage(inputMessage)
                                inputMessage = ""
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessagesList(
    chatHistory: List<com.example.ai.RoleplayTurn>,
    viewModel: MainViewModel,
    scenarioCharacterId: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(chatHistory) { msg ->
            val isAi = msg.speakerRole == "AI"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = if (isAi) Arrangement.Start else Arrangement.End
            ) {
                Card(
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isAi) 4.dp else 16.dp,
                        bottomEnd = if (isAi) 16.dp else 4.dp
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
                                text = msg.speakerNameAr,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isAi) Gold700 else Emerald100
                            )

                            if (isAi) {
                                // Dual Male and Female Voice Buttons
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Male Voice
                                    Surface(
                                        onClick = {
                                            viewModel.audioService.speakAsMale(msg.textAr)
                                        },
                                        shape = CircleShape,
                                        color = Emerald100,
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(text = "🧔", fontSize = 13.sp)
                                        }
                                    }

                                    // Female Voice
                                    Surface(
                                        onClick = {
                                            viewModel.audioService.speakAsFemale(msg.textAr)
                                        },
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
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = msg.textAr,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            color = if (isAi) MaterialTheme.colorScheme.onSurface else Color.White
                        )

                        if (msg.textId.isNotBlank()) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg.textId,
                                fontSize = 11.sp,
                                color = if (isAi) Color.Gray else Emerald100.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickSuggestionsRow(
    scenarioId: String,
    onSelect: (String) -> Unit
) {
    val suggestions = when (scenarioId) {
        "rp_restaurant" -> listOf("أُرِيدُ الأَرُزَّ وَالدَّجَاجَ", "أُرِيدُ المَاءَ مِنْ فَضْلِكَ", "الحِسَابُ مِنْ فَضْلِكَ")
        "rp_taxi" -> listOf("إِلَى الجَامِعَةِ مِنْ فَضْلِكَ", "كَمِ الأُجْرَةُ؟", "شُكْرًا لَكَ")
        "rp_doctor" -> listOf("أَشْعُرُ بِالصُّدَاعِ", "مُنْذُ يَوْمَيْنِ", "شُكْرًا يَا دُكْتُورَةُ")
        else -> listOf("أَنَا بِخَيْرٍ الحَمْدُ لِلَّهِ", "نَعَمْ، فِكْرَةٌ رَائِعَةٌ", "مَا رَأْيُكَ فِي قَهْوَةٍ؟")
    }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(suggestions) { phrase ->
            Surface(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onSelect(phrase) },
                color = Emerald100
            ) {
                Text(
                    text = phrase,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Emerald800,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatInputBar(
    inputMessage: String,
    onMessageChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = onMessageChange,
                placeholder = { Text("اكتب ردك بالعربية هنا...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("roleplay_text_input"),
                shape = RoundedCornerShape(16.dp),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(6.dp))

            IconButton(
                onClick = onSend,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Emerald800)
                    .testTag("send_roleplay_btn")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "إرسال",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
