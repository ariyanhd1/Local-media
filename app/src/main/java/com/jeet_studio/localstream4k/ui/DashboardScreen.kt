package com.jeet_studio.localstream4k.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeet_studio.localstream4k.service.ServerForegroundService
import com.jeet_studio.localstream4k.ui.theme.*

@Composable
fun DashboardScreen() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var isServerRunning by remember { mutableStateOf(true) }
    var showSettings by remember { mutableStateOf(false) }
    var activeTab by remember { mutableStateOf("server") }

    val primaryUrl = "http://192.168.31.217:8080"
    val secondaryUrl = "192.0.0.4:8080"
    val tailscaleUrl = "http://100.84.192.45:8080"

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "LocalStream",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        color = PeachAccent.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "PRO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PeachAccent,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IconButton(onClick = {}) { Icon(Icons.Default.Language, "Web", tint = Color.LightGray) }
                    IconButton(onClick = {}) { Icon(Icons.Default.Star, "Favorites", tint = Color.LightGray) }
                    IconButton(onClick = { showSettings = true }) { Icon(Icons.Default.Settings, "Settings", tint = Color.LightGray) }
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1B1410)) {
                NavigationBarItem(
                    selected = activeTab == "server",
                    onClick = { activeTab = "server" },
                    icon = { Icon(Icons.Default.Dns, "My Server") },
                    label = { Text("My Server", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == "files",
                    onClick = { activeTab = "files" },
                    icon = { Icon(Icons.Default.Folder, "File Manager") },
                    label = { Text("File Manager", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == "player",
                    onClick = { activeTab = "player" },
                    icon = { Icon(Icons.Default.PlayArrow, "Player") },
                    label = { Text("Player", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == "renderers",
                    onClick = { activeTab = "renderers" },
                    icon = { Icon(Icons.Default.Tv, "Renderers") },
                    label = { Text("Renderers", fontSize = 10.sp) }
                )
                NavigationBarItem(
                    selected = activeTab == "adb",
                    onClick = { activeTab = "adb" },
                    icon = { Icon(Icons.Default.Android, "Adb") },
                    label = { Text("Adb", fontSize = 10.sp) }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main Rocket Server Card (Screenshot 4)
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(listOf(CardRustDark, CardRustLight)),
                        RoundedCornerShape(24.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "🚀 ", fontSize = 22.sp)
                        Text(
                            text = "LocalStream",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Black,
                            color = Color.White
                        )
                    }

                    Text(
                        text = if (isServerRunning)
                            "Media server running at $primaryUrl (2179 media files loaded)"
                        else "Server stopped.",
                        fontSize = 12.sp,
                        color = Color(0xFFFFD8C7)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                isServerRunning = !isServerRunning
                                val intent = Intent(context, ServerForegroundService::class.java)
                                if (isServerRunning) context.startService(intent) else context.stopService(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = PeachButton),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = if (isServerRunning) "⏹ Stop Server" else "▶ Start Server",
                                color = Color(0xFF3D1D0E),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            color = Color(0xFF3B1E0E).copy(alpha = 0.8f),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Language, null, tint = PeachButton, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("0", color = PeachButton, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    Text(
                        text = "{ Stop me if you are done Streaming }",
                        fontSize = 12.sp,
                        color = Color(0xFFFDCAB9),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

            // Server Access URLs Card (Screenshot 4)
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = SurfaceDark),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "📡 Server Access URLs",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text("Server Access", fontSize = 12.sp, color = Color.Gray)

                    // Secondary IP
                    Surface(
                        color = Color(0xFF19110D),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(secondaryUrl, color = Color.LightGray, fontSize = 12.sp)
                            IconButton(onClick = { clipboardManager.setText(AnnotatedString(secondaryUrl)) }) {
                                Icon(Icons.Default.ContentCopy, "Copy", tint = Color.Gray)
                            }
                        }
                    }

                    // Primary Wi-Fi IP
                    Surface(
                        color = Color(0xFF19110D),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(primaryUrl, color = PeachAccent, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            IconButton(onClick = { clipboardManager.setText(AnnotatedString(primaryUrl)) }) {
                                Icon(Icons.Default.ContentCopy, "Copy", tint = Color.Gray)
                            }
                        }
                    }

                    // Tailscale IP
                    Surface(
                        color = Color(0xFF19110D),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Tailscale: $tailscaleUrl", color = Color(0xFF93C5FD), fontSize = 12.sp)
                            IconButton(onClick = { clipboardManager.setText(AnnotatedString(tailscaleUrl)) }) {
                                Icon(Icons.Default.ContentCopy, "Copy", tint = Color.Gray)
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSettings) {
        ServerSettingsSheet(onDismiss = { showSettings = false })
    }
}
