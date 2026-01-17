package me.app.yummy.compose.widgets.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.app.yummy.compose.theme.BUTTON_HEIGHT

@Composable
fun AppButton(modifier: Modifier = Modifier, text: String, onClick: () -> Unit) {
    Button(onClick = onClick, modifier = modifier.fillMaxWidth().height(BUTTON_HEIGHT)) {
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}