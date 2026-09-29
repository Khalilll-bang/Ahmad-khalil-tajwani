package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audio.AudioService
import com.example.data.seed.SeedLocations
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700

@Composable
fun ArabicAudioPlayer(
    textAr: String,
    speakerId: String,
    speakerNameAr: String,
    audioService: AudioService,
    modifier: Modifier = Modifier,
    autoPlayInitial: Boolean = false,
    showVoiceSwitchChips: Boolean = true
) {
    var isTextVisible by remember { mutableStateOf(true) }
    var currentSpeed by remember { mutableStateOf(1.0f) }
    var explicitGender by remember { mutableStateOf<String?>(null) } // null = context default, "MALE", "FEMALE"

    val isPlaying by audioService.isPlaying.collectAsState()
    val isFemaleVoiceActive by audioService.isFemaleVoiceActive.collectAsState()

    val speakerProfile = SeedLocations.speakers[speakerId]
    val defaultIsFemale = speakerProfile?.gender.equals("FEMALE", ignoreCase = true)
    val currentIsFemale = when (explicitGender) {
        "FEMALE" -> true
        "MALE" -> false
        else -> defaultIsFemale
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("arabic_audio_player"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Speaker header with context & gender badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(if (currentIsFemale) Color(0xFFFCE7F3) else Emerald100),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (currentIsFemale) "🧕" else "🧔",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = when (explicitGender) {
                                    "FEMALE" -> if (defaultIsFemale) (speakerNameAr.ifBlank { speakerProfile?.nameAr ?: "المتحدثة" }) else "صوت نسائي (مريم)"
                                    "MALE" -> if (!defaultIsFemale) (speakerNameAr.ifBlank { speakerProfile?.nameAr ?: "المتحدث" }) else "صوت رجالي (أحمد)"
                                    else -> speakerNameAr.ifBlank { speakerProfile?.nameAr ?: "المتحدث" }
                                },
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Emerald800
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            // Gender indicator tag
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (currentIsFemale) Color(0xFFFBCFE8) else Emerald100
                            ) {
                                Text(
                                    text = if (currentIsFemale) "صوت نسائي" else "صوت رجالي",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (currentIsFemale) Color(0xFF9D174D) else Emerald800,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        Text(
                            text = if (currentIsFemale) "نطق عربي فصيح بنبرة نسائية" else "نطق عربي فصيح بنبرة رجالية",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }

                // Show / Hide Text Toggle
                Surface(
                    onClick = { isTextVisible = !isTextVisible },
                    shape = RoundedCornerShape(20.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 1.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (isTextVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "تبديل إظهار النص",
                            modifier = Modifier.size(16.dp),
                            tint = Emerald700
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (isTextVisible) "إخفاء" else "إظهار",
                            fontSize = 11.sp,
                            color = Emerald700
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Arabic text display
            if (isTextVisible && textAr.isNotBlank()) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = textAr,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        lineHeight = 32.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Gender Switcher Chips (Male vs Female context switcher)
            if (showVoiceSwitchChips) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تبديل الصوت:",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )

                    // Male voice option
                    val isMaleSelected = !currentIsFemale
                    Surface(
                        onClick = {
                            explicitGender = "MALE"
                            audioService.speak(
                                text = textAr,
                                speakerId = if (!defaultIsFemale) speakerId else "teacher_male_01",
                                customSpeed = currentSpeed,
                                overrideGender = "MALE"
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isMaleSelected) Emerald800 else MaterialTheme.colorScheme.surface,
                        tonalElevation = if (isMaleSelected) 3.dp else 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🧔", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "رجالي (أحمد)",
                                fontSize = 11.sp,
                                fontWeight = if (isMaleSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isMaleSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    // Female voice option
                    val isFemaleSelected = currentIsFemale
                    Surface(
                        onClick = {
                            explicitGender = "FEMALE"
                            audioService.speak(
                                text = textAr,
                                speakerId = if (defaultIsFemale) speakerId else "teacher_female_01",
                                customSpeed = currentSpeed,
                                overrideGender = "FEMALE"
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        color = if (isFemaleSelected) Color(0xFFBE185D) else MaterialTheme.colorScheme.surface,
                        tonalElevation = if (isFemaleSelected) 3.dp else 1.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "🧕", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "نسائي (فاطمة)",
                                fontSize = 11.sp,
                                fontWeight = if (isFemaleSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isFemaleSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Audio Player Controls & Speeds
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Play / Pause / Replay Buttons
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (currentIsFemale) Color(0xFFBE185D) else Emerald800)
                            .clickable {
                                audioService.speak(
                                    text = textAr,
                                    speakerId = speakerId,
                                    customSpeed = currentSpeed,
                                    overrideGender = explicitGender
                                )
                            }
                            .testTag("play_audio_btn"),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.VolumeUp else Icons.Default.PlayArrow,
                            contentDescription = "تشغيل الصوت العربي",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            audioService.speak(
                                text = textAr,
                                speakerId = speakerId,
                                customSpeed = currentSpeed,
                                overrideGender = explicitGender
                            )
                        },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Replay,
                            contentDescription = "إعادة التشغيل",
                            tint = if (currentIsFemale) Color(0xFFBE185D) else Emerald700
                        )
                    }
                }

                // Speed Selector (0.75x, 1x, 1.25x)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val speeds = listOf(0.75f, 1.0f, 1.25f)
                    speeds.forEach { speed ->
                        val isSelected = currentSpeed == speed
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Gold700 else MaterialTheme.colorScheme.surface)
                                .clickable {
                                    currentSpeed = speed
                                    audioService.setSpeed(speed)
                                }
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${speed}x",
                                fontSize = 11.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    }
}
