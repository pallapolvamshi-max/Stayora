package com.stayora.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stayora.app.ui.theme.PurplePrimary
import com.stayora.app.ui.theme.PurpleTint

@Composable
fun CompatibilityBadge(
    score: Int,
    modifier: Modifier = Modifier,
    label: String = "Match"
) {
    Row(
        modifier = modifier   git --version
            .clip(RoundedCornerShape(12.dp))
            .background(PurpleTint)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = PurplePrimary,
            modifier = Modifier.size(13.dp)
        )
        Text(
            text = " $score% $label",
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = PurplePrimary
            )
        )
    }
}
