package app.abhigarala.pma.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.data.PasswordEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountSheet(
    isFromEdit: Boolean = false,
    onShowAddSheet: (Boolean) -> Unit,
    newTitle: String,
    onTitleChange: (String) -> Unit,
    newUsername: String,
    onUsernameChange: (String) -> Unit,
    newPassword: String,
    onPasswordChange: (String) -> Unit,
    onAdd: (PasswordEntry) -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = { onShowAddSheet(false) },
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = newTitle,
                onValueChange = { onTitleChange(it) },
                label = { Text("Account Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = newUsername,
                onValueChange = { onUsernameChange(it) },
                label = { Text("Username / Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = newPassword,
                onValueChange = { onPasswordChange(it) },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(
                onClick = {
                    onAdd(
                        (PasswordEntry(
                            title = newTitle, username = newUsername, password = newPassword
                        ))
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, contentColor = Color.White
                )
            ) {
                Text(text = changeSaveButtonName(isFromEdit))
            }
        }
    }


}

fun changeSaveButtonName(bool: Boolean): String {
    return if (bool) "Update Account" else "Add New Account"
}