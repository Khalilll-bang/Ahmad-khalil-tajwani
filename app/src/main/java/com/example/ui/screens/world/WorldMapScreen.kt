package com.example.ui.screens.world

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LocationModel
import com.example.data.model.MissionModel
import com.example.data.seed.SeedFinalChallenge
import com.example.ui.theme.Emerald100
import com.example.ui.theme.Emerald700
import com.example.ui.theme.Emerald800
import com.example.ui.theme.Gold100
import com.example.ui.theme.Gold600
import com.example.ui.theme.Gold700
import com.example.ui.theme.SuccessGreen
import com.example.viewmodel.MainViewModel

@Composable
fun WorldMapScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val completedIds by viewModel.completedMissionIds.collectAsState()
    val allLocations = viewModel.repository.allLocations
    val allMissions = viewModel.repository.allMissions

    var selectedLevelTab by remember { mutableStateOf(1) }
    var selectedLocationForModal by remember { mutableStateOf<LocationModel?>(null) }
    var selectedLocationForDesktop by remember { mutableStateOf<LocationModel?>(allLocations.firstOrNull()) }

    val levelNames = listOf(
        1 to "المستوى 1: الحياة اليومية",
        2 to "المستوى 2: حياتي الدراسية",
        3 to "المستوى 3: الخدمات العامة",
        4 to "المستوى 4: السفر والتنقل",
        5 to "المستوى 5: المجتمع والتواصل",
        6 to "المستوى 6: التحدي الكبير"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("world_map_screen")
    ) {
        val isLaptopLayout = maxWidth >= 720.dp

        Column(modifier = Modifier.fillMaxSize()) {
            // Top Header
            Surface(
                color = Emerald800,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🌍", fontSize = 24.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "خريطة العالم العربي (21 موقعًا)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Text(
                        text = "تنقل بين المحطات وعش المواقف الحقيقية باللغة العربية",
                        fontSize = 12.sp,
                        color = Emerald100
                    )
                }
            }

            // Level Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedLevelTab - 1,
                containerColor = MaterialTheme.colorScheme.surface,
                edgePadding = 12.dp
            ) {
                levelNames.forEach { (levelNum, label) ->
                    Tab(
                        selected = selectedLevelTab == levelNum,
                        onClick = {
                            selectedLevelTab = levelNum
                            if (levelNum < 6) {
                                val firstInLevel = allLocations.find { it.levelNumber == levelNum }
                                if (firstInLevel != null) selectedLocationForDesktop = firstInLevel
                            }
                        },
                        text = {
                            Text(
                                text = "L$levelNum",
                                fontWeight = if (selectedLevelTab == levelNum) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        }
                    )
                }
            }

            // Current Level Description banner
            val currentLevelTitle = levelNames.find { it.first == selectedLevelTab }?.second ?: ""
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentLevelTitle,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Emerald800,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            // Locations List / Master-Detail split
            if (selectedLevelTab == 6) {
                // Level 6: Final Challenge
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(if (isLaptopLayout) 0.6f else 1.0f)
                            .clickable {
                                viewModel.startMission(SeedFinalChallenge.finalChallengeMission)
                            },
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = Gold100),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🌟", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "التحدي الكبير: يوم كامل باللغة العربية",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gold700
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "محاكاة شاملة تختبر المهارات الست والتعامل مع الطوارئ والمواقف المعقدة.",
                                fontSize = 13.sp,
                                color = Color.Black.copy(alpha = 0.7f),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.startMission(SeedFinalChallenge.finalChallengeMission) },
                                colors = ButtonDefaults.buttonColors(containerColor = Gold700),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(Icons.Default.PlayArrow, contentDescription = null)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "خوض التحدي الأكبر (+250 XP)")
                            }
                        }
                    }
                }
            } else {
                val levelLocations = allLocations.filter { it.levelNumber == selectedLevelTab }

                if (isLaptopLayout) {
                    // Laptop Split Screen: Master List on Left, Detail Pane on Right
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Master Locations list (weight 1f)
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(levelLocations) { loc ->
                                val locationMissions = allMissions.filter { it.locationId == loc.id }
                                val completedCount = locationMissions.count { completedIds.contains(it.id) }
                                val isAllCompleted = completedCount == locationMissions.size && locationMissions.isNotEmpty()
                                val isSelected = selectedLocationForDesktop?.id == loc.id

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedLocationForDesktop = loc }
                                        .testTag("location_card_${loc.id}"),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (isSelected) Emerald100.copy(alpha = 0.6f) else MaterialTheme.colorScheme.surface
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 3.dp else 1.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(14.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(text = loc.icon, fontSize = 28.sp)
                                            Spacer(modifier = Modifier.width(12.dp))
                                            Column {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Text(text = loc.nameAr, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                                    Spacer(modifier = Modifier.width(6.dp))
                                                    Text(text = "(${loc.nameId})", fontSize = 12.sp, color = Color.Gray)
                                                }
                                                Text(text = "${locationMissions.size} مهام محاكاة", fontSize = 11.sp, color = Color.Gray)
                                            }
                                        }

                                        if (isAllCompleted) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = "مكتمل",
                                                tint = SuccessGreen,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        } else {
                                            Surface(
                                                color = Emerald100,
                                                shape = RoundedCornerShape(8.dp)
                                            ) {
                                                Text(
                                                    text = "$completedCount / ${locationMissions.size}",
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Emerald800,
                                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Detail Pane on Right (weight 1.1f)
                        val activeLoc = selectedLocationForDesktop ?: levelLocations.firstOrNull()
                        if (activeLoc != null) {
                            val activeMissions = allMissions.filter { it.locationId == activeLoc.id }
                            Card(
                                modifier = Modifier
                                    .weight(1.1f)
                                    .fillMaxHeight(),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(18.dp)
                                        .verticalScroll(rememberScrollState()),
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = activeLoc.icon, fontSize = 36.sp)
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(
                                                text = activeLoc.nameAr,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 20.sp,
                                                color = Emerald800
                                            )
                                            Text(
                                                text = activeLoc.nameId,
                                                fontSize = 13.sp,
                                                color = Color.Gray
                                            )
                                        }
                                    }

                                    Text(
                                        text = activeLoc.description,
                                        fontSize = 13.sp,
                                        lineHeight = 18.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Divider()

                                    Text(
                                        text = "المهام الميدانية في هذا الموقع:",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald800
                                    )

                                    activeMissions.forEach { mission ->
                                        val isDone = completedIds.contains(mission.id)
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(12.dp))
                                                .clickable { viewModel.startMission(mission) },
                                            color = if (isDone) Emerald100.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(modifier = Modifier.weight(1f)) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text(
                                                            text = "المهمة ${mission.orderNumber}: ",
                                                            fontSize = 12.sp,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Emerald800
                                                        )
                                                        Text(
                                                            text = mission.titleAr,
                                                            fontSize = 14.sp,
                                                            fontWeight = FontWeight.Bold
                                                        )
                                                    }
                                                    Text(
                                                        text = mission.objective,
                                                        fontSize = 12.sp,
                                                        color = Color.Gray,
                                                        maxLines = 2
                                                    )
                                                }

                                                Spacer(modifier = Modifier.width(8.dp))

                                                Button(
                                                    onClick = { viewModel.startMission(mission) },
                                                    shape = RoundedCornerShape(10.dp),
                                                    colors = ButtonDefaults.buttonColors(
                                                        containerColor = if (isDone) Emerald700 else Gold600
                                                    )
                                                ) {
                                                    Text(text = if (isDone) "إعادة" else "ابدأ", fontSize = 12.sp)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // Mobile Layout: Single Scrollable Column with Sheet on tap
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(levelLocations) { loc ->
                            val locationMissions = allMissions.filter { it.locationId == loc.id }
                            val completedCount = locationMissions.count { completedIds.contains(it.id) }
                            val isAllCompleted = completedCount == locationMissions.size && locationMissions.isNotEmpty()

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedLocationForModal = loc }
                                    .testTag("location_card_${loc.id}"),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(text = loc.icon, fontSize = 32.sp)
                                        Spacer(modifier = Modifier.width(14.dp))
                                        Column {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(text = loc.nameAr, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                                                Spacer(modifier = Modifier.width(6.dp))
                                                Text(text = "(${loc.nameId})", fontSize = 12.sp, color = Color.Gray)
                                            }
                                            Text(text = loc.description, fontSize = 11.sp, color = Color.Gray, maxLines = 1)
                                        }
                                    }

                                    if (isAllCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.CheckCircle,
                                            contentDescription = "مكتمل",
                                            tint = SuccessGreen,
                                            modifier = Modifier.size(22.dp)
                                        )
                                    } else {
                                        Surface(
                                            color = Emerald100,
                                            shape = RoundedCornerShape(10.dp)
                                        ) {
                                            Text(
                                                text = "$completedCount / ${locationMissions.size}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Emerald800,
                                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Modal Sheet for Mobile
        if (!isLaptopLayout) {
            selectedLocationForModal?.let { loc ->
                val locationMissions = allMissions.filter { it.locationId == loc.id }
                LocationMissionsSheet(
                    location = loc,
                    missions = locationMissions,
                    completedIds = completedIds,
                    onDismiss = { selectedLocationForModal = null },
                    onSelectMission = { mission ->
                        selectedLocationForModal = null
                        viewModel.startMission(mission)
                    }
                )
            }
        }
    }
}

@Composable
fun LocationMissionsSheet(
    location: LocationModel,
    missions: List<MissionModel>,
    completedIds: List<String>,
    onDismiss: () -> Unit,
    onSelectMission: (MissionModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = location.icon, fontSize = 26.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(text = location.nameAr, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text(text = location.nameId, fontSize = 12.sp, color = Color.Gray)
                }
            }
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(text = location.description, fontSize = 12.sp, color = Color.Gray)
                Divider()

                missions.forEach { mission ->
                    val isDone = completedIds.contains(mission.id)
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onSelectMission(mission) },
                        color = if (isDone) Emerald100.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "المهمة ${mission.orderNumber}: ",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Emerald800
                                    )
                                    Text(
                                        text = mission.titleAr,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                Text(
                                    text = mission.objective,
                                    fontSize = 11.sp,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                            Text(
                                text = "+${mission.xp} XP",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Gold700
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("إغلاق")
            }
        }
    )
}
