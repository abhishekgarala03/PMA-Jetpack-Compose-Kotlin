package app.abhigarala.pma.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.PMApp.Companion.RSA_PRIVATE_KEY
import app.abhigarala.pma.cypher.EncryptionUtils
import app.abhigarala.pma.data.PasswordEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailSheet(
    entry: PasswordEntry,
    onDismiss: () -> Unit,
    onEdit: (PasswordEntry) -> Unit,
    onDelete: (PasswordEntry) -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Account Details", style = MaterialTheme.typography.titleLarge)

            Text("Account", style = MaterialTheme.typography.labelSmall)
            Text(entry.title, style = MaterialTheme.typography.bodyLarge)
            Text("Username", style = MaterialTheme.typography.labelSmall)
            Text(entry.username, style = MaterialTheme.typography.bodyLarge)

            Text("Password", style = MaterialTheme.typography.labelSmall)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    if (visible) {
                        EncryptionUtils.decrypt(
                            entry.password,
                            RSA_PRIVATE_KEY
                        )
                    } else {
                        val passLength = EncryptionUtils.decrypt(
                            entry.password,
                            RSA_PRIVATE_KEY
                        ).length
                        "*".repeat(passLength)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = { visible = !visible }) {
                    Icon(
                        imageVector = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (visible) "Hide" else "Show"
                    )
                }
            }

            // Actions
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = { onEdit(entry) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(24.dp)
                ) { Text("Edit") }

                Button(
                    onClick = { onDelete(entry) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(24.dp)
                ) { Text("Delete") }
            }
        }
    }
}