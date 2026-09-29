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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.audio.AudioService
import com.example.data.model.VocabularyEntity
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen

@Composable
fun ContextWordDialog(
    vocab: VocabularyEntity,
    audioService: AudioService,
    onDismiss: () -> Unit,
    onToggleSave: (String) -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("context_word_dialog")
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Header with close button and category
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
                            text = "القاموس السياقي",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Emerald800,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "إغلاق")
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Arabic Word in large font with Tashkeel
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = vocab.tashkeel.ifBlank { vocab.wordAr },
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald900Color(),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = vocab.meaningId,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Gold700,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Audio play buttons (Male and Female native Arabic speakers)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Male Voice
                        Surface(
                            onClick = {
                                audioService.speakAsMale(vocab.tashkeel.ifBlank { vocab.wordAr })
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = Emerald800,
                            tonalElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🧔", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "صوت رجالي",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // Female Voice
                        Surface(
                            onClick = {
                                audioService.speakAsFemale(vocab.tashkeel.ifBlank { vocab.wordAr })
                            },
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFBE185D),
                            tonalElevation = 2.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "🧕", fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "صوت نسائي",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Divider()
                Spacer(modifier = Modifier.height(12.dp))

                // Example sentence
                Text(
                    text = "مثال سياقي (Contoh Kalimat):",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))

                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            text = vocab.exampleAr,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = vocab.exampleId,
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Mastery Indicator
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "مستوى الإتقان: ${vocab.mastery}%",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    val masteryStatus = when {
                        vocab.mastery >= 90 -> "متقن (Mastered)"
                        vocab.mastery >= 70 -> "جيد (Good)"
                        vocab.mastery >= 40 -> "قيد التعلم (Learning)"
                        else -> "يحتاج مراجعة (Need Review)"
                    }
                    Text(
                        text = masteryStatus,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (vocab.mastery >= 70) SuccessGreen else Gold700
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Save Word Button
                Button(
                    onClick = { onToggleSave(vocab.id) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (vocab.isSaved) Gold700 else Emerald800
                    )
                ) {
                    Icon(
                        imageVector = if (vocab.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (vocab.isSaved) "تم الحفظ في المفردات ✓" else "+ حفظ الكلمة"
                    )
                }
            }
        }
    }
}

@Composable
private fun Emerald900Color(): Color {
    return Emerald800
}
