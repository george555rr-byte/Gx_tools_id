package com.gx.tools.id.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.gx.tools.id.data.AppData
import com.gx.tools.id.ui.theme.*

@Composable
fun IdDialog(app: AppData, onDismiss: () -> Unit) {
    val ctx = LocalContext.current

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(SurfaceDark, RoundedCornerShape(22.dp))
                .border(1.dp, NeonPurple, RoundedCornerShape(22.dp))
                .padding(18.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "GX_ID",
                        color = NeonPurple,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        letterSpacing = 1.sp
                    )
                    Text(
                        text = "Tracker IDs",
                        color = TextGray,
                        fontSize = 11.sp
                    )
                }
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = TextGray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onDismiss() }
                )
            }

            Spacer(Modifier.height(14.dp))

            // App Info Card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BgDark, RoundedCornerShape(14.dp))
                    .border(1.dp, BorderPurple, RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Text(app.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Spacer(Modifier.height(2.dp))
                Text(app.packageName, color = TextGray, fontSize = 11.sp)
                Spacer(Modifier.height(6.dp))
                Row {
                    Text("v${app.version}", color = NeonPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(Color(app.trackerColor).copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(1.dp, Color(app.trackerColor).copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(app.tracker, color = Color(app.trackerColor), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(14.dp))

            // IDs
            Text("IDENTIFIERS", color = NeonPurple, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.8.sp)
            Spacer(Modifier.height(8.dp))

            if (app.tracker == "AppsFlyer") {
                IdRow("GAID", app.gaid, ctx)
                IdRow("AF_ID", app.afId, ctx)
                IdRow("INSTALL_ID", app.installId, ctx)
            } else if (app.tracker == "Singular") {
                IdRow("GAID", app.gaid, ctx)
                IdRow("SINGULAR_ID", app.singularId, ctx)
                IdRow("INSTALL_ID", app.installId, ctx)
            } else if (app.tracker == "Adjust") {
                IdRow("GAID", app.gaid, ctx)
                IdRow("ADJUST_ID", app.adjustId, ctx)
                IdRow("INSTALL_ID", app.installId, ctx)
            } else if (app.tracker == "Amazon") {
                IdRow("GAID", app.gaid, ctx)
                IdRow("AMAZON_ID", app.amazonId, ctx)
                IdRow("INSTALL_ID", app.installId, ctx)
            } else {
                IdRow("GAID", app.gaid, ctx)
                IdRow("UUID", app.uuid, ctx)
                IdRow("DEVICE_ID", app.deviceId, ctx)
            }

            Spacer(Modifier.height(16.dp))

            // Copy All Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(listOf(NeonPurple, NeonPurple2)),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .clickable {
                        val all = buildString {
                            append("App: ${app.name}\n")
                            append("Package: ${app.packageName}\n")
                            append("Tracker: ${app.tracker}\n")
                            append("GAID: ${app.gaid}\n")
                            append("AF_ID: ${app.afId}\n")
                            append("INSTALL_ID: ${app.installId}\n")
                            append("UUID: ${app.uuid}\n")
                            append("DEVICE_ID: ${app.deviceId}\n")
                            append("SINGULAR_ID: ${app.singularId}\n")
                            append("ADJUST_ID: ${app.adjustId}\n")
                            append("AMAZON_ID: ${app.amazonId}")
                        }
                        copyToClipboard(ctx, "GX_ID", all)
                        Toast.makeText(ctx, "All IDs copied!", Toast.LENGTH_SHORT).show()
                    }
                    .padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("COPY ALL", color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp, letterSpacing = 1.sp)
            }
        }
    }
}

@Composable
private fun IdRow(label: String, value: String, ctx: Context) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .background(BgDark, RoundedCornerShape(12.dp))
            .border(1.dp, BorderPurple, RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Text(label, color = NeonPurple, fontSize = 10.sp, fontWeight = FontWeight.Bold, letterSpacing = 0.6.sp)
        Spacer(Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = value,
                color = TextWhite,
                fontSize = 11.sp,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "Copy",
                tint = NeonPurple,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        copyToClipboard(ctx, label, value)
                        Toast.makeText(ctx, "$label copied!", Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }
}

private fun copyToClipboard(ctx: Context, label: String, value: String) {
    val cm = ctx.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cm.setPrimaryClip(ClipData.newPlainText(label, value))
}
