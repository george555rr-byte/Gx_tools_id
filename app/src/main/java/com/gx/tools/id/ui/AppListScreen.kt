package com.gx.tools.id.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gx.tools.id.data.AppData
import com.gx.tools.id.data.Scanner
import com.gx.tools.id.ui.theme.*

@Composable
fun AppListScreen(scanner: Scanner) {
    var apps by remember { mutableStateOf<List<AppData>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var selected by remember { mutableStateOf<AppData?>(null) }

    LaunchedEffect(Unit) {
        apps = scanner.scan()
        loading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDark)
    ) {
        // Header
        Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "GX_TOOLS_ID",
                        color = NeonPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Tracker Scanner · v1.0",
                        color = TextGray,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(AccentGreen.copy(alpha = 0.15f), RoundedCornerShape(20.dp))
                        .border(1.dp, AccentGreen.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "ARMED",
                        color = AccentGreen,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 1.sp
                    )
                }
            }

            Spacer(Modifier.height(18.dp))

            // Stats
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceDark, RoundedCornerShape(18.dp))
                    .border(1.dp, BorderPurple, RoundedCornerShape(18.dp))
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("APPS SCANNED", color = NeonPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("${apps.size}", color = TextWhite, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text("TRACKERS FOUND", color = NeonPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
                    Spacer(Modifier.height(4.dp))
                    Text("${apps.count { it.tracker != "None" }}", color = AccentGreen, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(14.dp))

            // Section Title
            Text(
                text = "TARGETS",
                color = NeonPurple,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
        }

        // List
        if (loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Scanning...", color = NeonPurple, fontSize = 16.sp)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(apps) { app ->
                    AppItem(app) { selected = app }
                }
                item { Spacer(Modifier.height(30.dp)) }
            }
        }
    }

    selected?.let { app ->
        IdDialog(app) { selected = null }
    }
}
