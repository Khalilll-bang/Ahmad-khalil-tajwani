package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.ui.theme.WarningOrange

@Composable
fun HintCard(
    currentHintLevel: Int,
    hint1: String,
    hint2: String,
    hint3: String,
    onRequestHint: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("hint_card"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Gold100.copy(alpha = 0.6f))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Gold700,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "نظام التلميحات الذكية (Sistem Petunjuk):",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = Gold700
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Hint 1: Petunjuk Ringan
            AnimatedVisibility(visible = currentHintLevel >= 1, enter = fadeIn() + expandVertically()) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "💡 المستوى 1 (خفيف): ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Gold700)
                        Text(text = hint1, fontSize = 12.sp)
                    }
                }
            }

            // Hint 2: Petunjuk Kosakata
            AnimatedVisibility(visible = currentHintLevel >= 2, enter = fadeIn() + expandVertically()) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "📚 المستوى 2 (مفردات): ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = WarningOrange)
                        Text(text = hint2, fontSize = 12.sp)
                    }
                }
            }

            // Hint 3: Petunjuk Struktur Kalimat
            AnimatedVisibility(visible = currentHintLevel >= 3, enter = fadeIn() + expandVertically()) {
                Surface(
                    color = Color.White,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🧱 المستوى 3 (بناء): ", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB91C1C))
                        Text(text = hint3, fontSize = 12.sp)
                    }
                }
            }

            if (currentHintLevel < 3) {
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedButton(
                    onClick = onRequestHint,
                    modifier = Modifier
                        .align(Alignment.End)
                        .testTag("request_hint_btn"),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Gold700)
                ) {
                    Icon(imageVector = Icons.Default.HelpOutline, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = when (currentHintLevel) {
                            0 -> "طلب تلميح خفيف (Hint 1)"
                            1 -> "طلب تلميح المفردات (Hint 2)"
                            else -> "طلب هيكل الإجابة (Hint 3)"
                        },
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
