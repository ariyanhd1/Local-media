package com.jeet_studio.localstream4k.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeet_studio.localstream4k.ui.theme.PeachAccent
import com.jeet_studio.localstream4k.ui.theme.PeachButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServerSettingsSheet(onDismiss: () -> Unit) {
    var serverName by remember { mutableStateOf("LocalStream") }
    var httpWebServer by remember { mutableStateOf(true) }
    var upnpStreaming by remember { mutableStateOf(true) }
    var smbSearch by remember { mutableStateOf(true) }
    var transcodeFlac by remember { mutableStateOf(false) }
    var requirePassword by remember { mutableStateOf(false) }
    var excludeAudio by remember { mutableStateOf(false) }
    var excludeVideo by remember { mutableStateOf(false) }
    var excludeImages by remember { mutableStateOf(false) }
    var excludeDocuments by remember { mutableStateOf(false) }
    var excludeFolders by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF241A15),
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Server Settings",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Section General (Screenshot 3)
            Text("General", color = PeachAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            OutlinedTextField(
                value = serverName,
                onValueChange = { serverName = it },
                label = { Text("Server Name (UPnP/DLNA)") },
                modifier = Modifier.fillMaxWidth()
            )

            SettingSwitchRow("HTTP Web Server", "Share files via Web Browser", httpWebServer) { httpWebServer = it }
            SettingSwitchRow("UPnP Content Streaming", "Share local files over UPnP", upnpStreaming) { upnpStreaming = it }
            SettingSwitchRow("SMB Server Search", "Discover Windows/SMB shares", smbSearch) { smbSearch = it }
            SettingSwitchRow("Transcode FLAC to WAV", "Fixes playback delay on FLAC", transcodeFlac) { transcodeFlac = it }

            Divider(color = Color(0xFF382821))

            // Section Security (Screenshot 2)
            Text("Security", color = PeachAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            SettingSwitchRow("Require Password", "For Web Browser Access", requirePassword) { requirePassword = it }

            Divider(color = Color(0xFF382821))

            // Section Background Execution (Screenshot 2)
            Text("Background Execution", color = PeachAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Disable Battery Optimization", color = Color.White, fontWeight = FontWeight.Medium)
                    Text("Prevents server from stopping when screen is off.", color = Color.Gray, fontSize = 12.sp)
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = PeachButton)
                ) {
                    Text("Configure", color = Color(0xFF241711))
                }
            }

            Divider(color = Color(0xFF382821))

            // Section Media Exclusions (Screenshot 1)
            Text("Media Exclusions", color = PeachAccent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            SettingSwitchRow("Exclude Audio", "Hide music/audio files", excludeAudio) { excludeAudio = it }
            SettingSwitchRow("Exclude Video", "Hide movies/video files", excludeVideo) { excludeVideo = it }
            SettingSwitchRow("Exclude Images", "Hide photos/image files", excludeImages) { excludeImages = it }
            SettingSwitchRow("Exclude Documents", "Hide PDF, text, and zip files", excludeDocuments) { excludeDocuments = it }
            SettingSwitchRow("Exclude Folders", "Hide folder navigation", excludeFolders) { excludeFolders = it }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.textButtonColors(contentColor = PeachAccent)
            ) {
                Text("Close")
            }
        }
    }
}

@Composable
fun SettingSwitchRow(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Color.White, fontWeight = FontWeight.Medium)
            Text(subtitle, color = Color.Gray, fontSize = 12.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color(0xFF201511),
                checkedTrackColor = PeachAccent
            )
        )
    }
}
