package app.abhigarala.pma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.data.PasswordEntry
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(
    entries: List<PasswordEntry>,
    onItemClick: (PasswordEntry) -> Unit = {},
    onAddEntry: (PasswordEntry) -> Unit = {}
) {
    // sheet visibility
    var showAddSheet by remember { mutableStateOf(false) }

    // form state
    var newTitle by remember { mutableStateOf("") }
    var newUsername by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }

    // Scaffold with FAB
    Scaffold(topBar = {
        TopAppBar(title = { Text("Password Manager") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = { showAddSheet = true }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = padding.calculateTopPadding() + 16.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(entries) { entry ->
                PasswordCard(entry = entry, onClick = { onItemClick(entry) })
            }
        }
    }

    // Bottom sheet
    if (showAddSheet) {
        AddAccountSheet(onShowAddSheet = {
            newTitle = ""
            newUsername = ""
            newPassword = ""
            showAddSheet = false
        },
            newTitle = newTitle,
            newUsername = newUsername,
            newPassword = newPassword,
            onTitleChange = {
                newTitle = it
            },
            onUsernameChange = {
                newUsername = it
            },
            onPasswordChange = {
                newPassword = it
            },
            onAdd = { passwordEntry ->
                onAddEntry(passwordEntry)
                newTitle = ""
                newUsername = ""
                newPassword = ""
                showAddSheet = false
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountSheet(
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
                            title = newTitle,
                            username = newUsername,
                            password = newPassword
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
                Text("Add New Account")
            }
        }
    }
}


@Composable
fun PasswordCard(entry: PasswordEntry, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Row {

                Text(
                    text = entry.title, style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .padding(top = 4.dp),
                    text = "*".repeat(10),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(

                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go to details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }


    }
}