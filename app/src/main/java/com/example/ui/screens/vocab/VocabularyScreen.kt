package com.example.ui.screens.vocab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.data.model.VocabularyEntity
import com.example.ui.components.ContextWordDialog
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.WarningOrange
import com.example.viewmodel.MainViewModel

@Composable
fun VocabularyScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val vocabList by viewModel.vocabularyList.collectAsState()
    val inspectingWord by viewModel.inspectingWord.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("ALL") }

    val categories = listOf(
        "ALL" to "الكل (Semua)",
        "SAVED" to "المحفوظة (Disimpan)",
        "WEAK" to "تحتاج مراجعة (Lemah)",
        "house" to "البيت",
        "food" to "الطعام",
        "directions" to "الاتجاهات",
        "study" to "الدراسة",
        "health" to "الصحة",
        "travel" to "السفر",
        "community" to "المجتمع"
    )

    val filteredList = vocabList.filter { item ->
        val matchesQuery = searchQuery.isBlank() ||
                item.wordAr.contains(searchQuery) ||
                item.tashkeel.contains(searchQuery) ||
                item.meaningId.contains(searchQuery, ignoreCase = true)

        val matchesCat = when (selectedCategory) {
            "ALL" -> true
            "SAVED" -> item.isSaved
            "WEAK" -> item.mastery < 60
            else -> item.category == selectedCategory
        }

        matchesQuery && matchesCat
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("vocabulary_screen")
    ) {
        val isLaptopLayout = maxWidth >= 720.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Surface(
                color = Emerald800,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "القاموس السياقي الحي",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "مفردات حية مع النطق العربي الأصيل (رجالي ونسائي)",
                                fontSize = 12.sp,
                                color = Emerald100
                            )
                        }

                        Surface(
                            color = Gold700,
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "${vocabList.size} كلمة",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Search Input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("ابحث بالعربية أو الإندونيسية...", color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp) },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = Color.White) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("vocab_search_input"),
                        shape = RoundedCornerShape(14.dp),
                        singleLine = true
                    )
                }
            }

            // Categories Filter Chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { (key, label) ->
                    val isSelected = selectedCategory == key
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable { selectedCategory = key },
                        color = if (isSelected) Emerald800 else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Text(
                            text = label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            // Vocabulary Content: Adaptive Grid on Laptop vs Column on Mobile
            if (isLaptopLayout) {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 340.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredList) { vocab ->
                        VocabCardItem(
                            vocab = vocab,
                            viewModel = viewModel,
                            onInspect = { viewModel.inspectWord(vocab) }
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    contentPadding = PaddingValues(bottom = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(filteredList) { vocab ->
                        VocabCardItem(
                            vocab = vocab,
                            viewModel = viewModel,
                            onInspect = { viewModel.inspectWord(vocab) }
                        )
                    }
                }
            }
        }
    }

    // Context Word Modal
    inspectingWord?.let { word ->
        ContextWordDialog(
            vocab = word,
            audioService = viewModel.audioService,
            onDismiss = { viewModel.closeWordInspector() },
            onToggleSave = { id -> viewModel.toggleSaveVocab(id) }
        )
    }
}

@Composable
private fun VocabCardItem(
    vocab: VocabularyEntity,
    viewModel: MainViewModel,
    onInspect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onInspect() }
            .testTag("vocab_card_${vocab.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Dual Voice Play Buttons (Male and Female)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Male Voice
                    Surface(
                        onClick = {
                            viewModel.audioService.speakAsMale(vocab.tashkeel.ifBlank { vocab.wordAr })
                        },
                        shape = CircleShape,
                        color = Emerald100,
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🧔", fontSize = 15.sp)
                        }
                    }

                    // Female Voice
                    Surface(
                        onClick = {
                            viewModel.audioService.speakAsFemale(vocab.tashkeel.ifBlank { vocab.wordAr })
                        },
                        shape = CircleShape,
                        color = Color(0xFFFCE7F3),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(text = "🧕", fontSize = 15.sp)
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = vocab.tashkeel.ifBlank { vocab.wordAr },
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold,
                        color = Emerald800
                    )
                    Text(
                        text = vocab.meaningId,
                        fontSize = 13.sp,
                        color = Color.Gray
                    )
                }
            }

            // Bookmark & Mastery badge
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when {
                        vocab.mastery >= 90 -> SuccessGreen.copy(alpha = 0.15f)
                        vocab.mastery >= 70 -> Emerald100
                        vocab.mastery >= 40 -> Gold100
                        else -> WarningOrange.copy(alpha = 0.15f)
                    }
                ) {
                    Text(
                        text = "${vocab.mastery}%",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = when {
                            vocab.mastery >= 90 -> SuccessGreen
                            vocab.mastery >= 70 -> Emerald800
                            vocab.mastery >= 40 -> Gold700
                            else -> WarningOrange
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(6.dp))

                IconButton(
                    onClick = { viewModel.toggleSaveVocab(vocab.id) },
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (vocab.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "حفظ الكلمة",
                        tint = if (vocab.isSaved) Gold700 else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}
