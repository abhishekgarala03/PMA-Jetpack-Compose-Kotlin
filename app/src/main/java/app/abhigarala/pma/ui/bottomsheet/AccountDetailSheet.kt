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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.R
import app.abhigarala.pma.data.PasswordEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountDetailSheet(
    entry: PasswordEntry,
    onDismiss: () -> Unit,
    onEdit: (PasswordEntry) -> Unit,
    onDelete: (PasswordEntry) -> Unit,
    decryptPassword: (String) -> String
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SheetHeader()
            AccountDetailsContent(entry, decryptPassword)
            ActionButtons(
                entry = entry,
                onEdit = onEdit,
                onDelete = onDelete
            )
        }
    }
}

@Composable
private fun SheetHeader() {
    Text(
        text = stringResource(R.string.account_details), // Use string resources
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun AccountDetailsContent(
    entry: PasswordEntry,
    decryptPassword: (String) -> String
) {
    val decryptedPassword = remember(entry.password) { decryptPassword(entry.password) }

    DetailItem(
        label = stringResource(R.string.account),
        value = entry.title
    )

    DetailItem(
        label = stringResource(R.string.username),
        value = entry.username
    )

    PasswordDetailItem(
        label = stringResource(R.string.password),
        encryptedValue = entry.password,
        decryptedValue = decryptedPassword
    )
}

@Composable
private fun DetailItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun PasswordDetailItem(
    label: String,
    encryptedValue: String,
    decryptedValue: String
) {
    var visible by remember { mutableStateOf(false) }

    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall
        )
        PasswordVisibilityRow(
            visible = visible,
            decryptedPassword = decryptedValue,
            onVisibilityToggle = { visible = !visible }
        )
    }
}

@Composable
private fun PasswordVisibilityRow(
    visible: Boolean,
    decryptedPassword: String,
    onVisibilityToggle: () -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = if (visible) decryptedPassword else "*".repeat(decryptedPassword.length),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onVisibilityToggle) {
            Icon(
                imageVector = if (visible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = stringResource(
                    if (visible) R.string.hide_password else R.string.show_password
                )
            )
        }
    }
}

@Composable
private fun ActionButtons(
    entry: PasswordEntry,
    onEdit: (PasswordEntry) -> Unit,
    onDelete: (PasswordEntry) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    )
    {
        ActionButton(
            text = stringResource(R.string.edit),
            onClick = { onEdit(entry) },
            modifier = Modifier.weight(1f,true),
            containerColor = MaterialTheme.colorScheme.primary
        )

        ActionButton(
            text = stringResource(R.string.delete),
            onClick = { onDelete(entry) },
            Modifier.weight(1f,true),
            containerColor = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = containerColor)
    ) {
        Text(text)
    }
}