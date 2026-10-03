package com.stayora.app.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.data.model.ChatMessage
import com.stayora.app.data.model.Property
import com.stayora.app.data.model.RoommateProfile
import com.stayora.app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(
    property: Property? = null,
    roommate: RoommateProfile? = null,
    messages: List<ChatMessage>,
    onSendMessage: (String) -> Unit,
    onBack: () -> Unit
) {
    if (property == null && roommate == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Conversation not found")
        }
        return
    }

    val context = LocalContext.current
    var inputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val titleName = roommate?.name ?: property?.ownerName ?: "Contact"
    val subtitleInfo = if (roommate != null) {
        "Active now • ${roommate.branch} (${roommate.college})"
    } else {
        "${property?.ownerRole ?: "Manager"} • ${property?.title ?: ""}"
    }

    val quickQuestions = if (roommate != null) {
        listOf(
            "👋 Hey! Looking for 2-sharing near campus?",
            "📚 What's your daily study & sleep schedule?",
            "🏠 Want to visit Greenfield PG together this weekend?",
            "💰 What is your target monthly budget?",
            "📍 Have you shortlisted any PG in Maisammaguda?"
        )
    } else {
        listOf(
            "Is 2-sharing bed vacant?",
            "Can I schedule a visit tomorrow at 5 PM?",
            "Is food included in rent?",
            "What is the deposit policy?",
            "What are the gate curfew timings?"
        )
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                shadowElevation = 3.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = CharcoalDark)
                    }

                    // Avatar with online dot
                    Box {
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(PurpleTint),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = titleName.take(1),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = PurplePrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                        Box(
                            modifier = Modifier
                                .size(11.dp)
                                .clip(CircleShape)
                                .background(VerifiedGreen)
                                .align(Alignment.BottomEnd)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = titleName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                maxLines = 1
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(Icons.Default.CheckCircle, contentDescription = "Verified", tint = VerifiedGreen, modifier = Modifier.size(14.dp))
                        }
                        Text(
                            text = subtitleInfo,
                            style = MaterialTheme.typography.bodySmall.copy(color = CharcoalLight),
                            maxLines = 1
                        )
                    }

                    // Quick call button
                    IconButton(
                        onClick = {
                            val phone = property?.ownerPhone ?: "+919876543210"
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
                            context.startActivity(intent)
                        }
                    ) {
                        Icon(Icons.Default.Phone, contentDescription = "Call", tint = PurplePrimary)
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                Column(modifier = Modifier.padding(bottom = 8.dp)) {
                    // Quick inquiry chips row
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(quickQuestions) { question ->
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(PurpleTint)
                                    .clickable {
                                        onSendMessage(question)
                                    }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = question,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PurpleDark,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }

                    // Input Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            placeholder = { 
                                Text(
                                    if (roommate != null) "Message ${titleName.substringBefore(" ")}..." else "Ask property manager...",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = GrayMuted)
                                ) 
                            },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(24.dp),
                            maxLines = 3,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = PurplePrimary,
                                unfocusedBorderColor = PurpleTint
                            )
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(
                            onClick = {
                                if (inputText.isNotBlank()) {
                                    onSendMessage(inputText.trim())
                                    inputText = ""
                                }
                            },
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(PurplePrimary)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(GrayUltraLight)
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = PurpleCardBg)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = PurplePrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (roommate != null) 
                                "Verified student chat. Connect safely and compare habits before booking a room together."
                            else 
                                "Direct owner chat via Stayora. No commission, 100% verified hostel inquiries.",
                            style = MaterialTheme.typography.bodySmall.copy(color = PurpleDark, fontSize = 11.sp)
                        )
                    }
                }
            }

            items(messages) { msg ->
                val isStudent = msg.isFromStudent

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (isStudent) Arrangement.End else Arrangement.Start
                ) {
                    Card(
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isStudent) 16.dp else 4.dp,
                            bottomEnd = if (isStudent) 4.dp else 16.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isStudent) PurplePrimary else Color.White
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                        modifier = Modifier.widthIn(max = 280.dp)
                    ) {
                        Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)) {
                            if (!isStudent) {
                                Text(
                                    text = msg.senderName,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = PurplePrimary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                            }

                            Text(
                                text = msg.text,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = if (isStudent) Color.White else CharcoalDark,
                                    lineHeight = 18.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = msg.timestamp,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (isStudent) PurpleTint.copy(alpha = 0.7f) else GrayMuted,
                                    fontSize = 9.sp
                                ),
                                modifier = Modifier.align(Alignment.End)
                            )
                        }
                    }
                }
            }
        }
    }
}
