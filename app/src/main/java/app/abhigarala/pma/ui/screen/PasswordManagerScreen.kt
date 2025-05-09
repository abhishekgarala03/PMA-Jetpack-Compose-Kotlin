package app.abhigarala.pma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import app.abhigarala.pma.PMApp.Companion.RSA_PRIVATE_KEY
import app.abhigarala.pma.cypher.EncryptionUtils
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.ui.bottomsheet.AccountDetailSheet
import app.abhigarala.pma.ui.bottomsheet.AddAccountSheet
import app.abhigarala.pma.ui.itemlayout.PasswordCard
import app.abhigarala.pma.utils.isValidPassword


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(
    entries: List<PasswordEntry>,
    onAddEntry: (PasswordEntry) -> Unit,
    onUpdateEntry: (PasswordEntry) -> Unit,
    onDeleteEntry: (PasswordEntry) -> Unit
) {

    var showAddSheet by remember { mutableStateOf(false) }
    var addTitle by remember { mutableStateOf("") }
    var addUsername by remember { mutableStateOf("") }
    var addPassword by remember { mutableStateOf("") }

    var showDetailSheet by remember { mutableStateOf(false) }
    var selectedEntry by remember { mutableStateOf<PasswordEntry?>(null) }

    var showEditSheet by remember { mutableStateOf(false) }
    var editTitle by remember { mutableStateOf("") }
    var editUsername by remember { mutableStateOf("") }
    var editPassword by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    Box {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Password Manager") })
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    showAddSheet = true
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { padding ->
            LazyColumn(
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding() + 16.dp,
                    bottom = padding.calculateBottomPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(entries) { entry ->
                    PasswordCard(entry = entry) {
                        selectedEntry = entry
                        showDetailSheet = true
                    }
                }
            }
        }

        if (showAddSheet) {
            AddAccountSheet(
                onShowAddSheet = { visible ->
                    showAddSheet = visible
                    if (!visible) {
                        addTitle = ""
                        addUsername = ""
                        addPassword = ""
                    }
                },
                newTitle = addTitle,
                onTitleChange = { addTitle = it },
                newUsername = addUsername,
                onUsernameChange = { addUsername = it },
                newPassword = addPassword,
                onPasswordChange = { addPassword = it },
                onAdd = { entry ->
                    when {
                        entry.title.isBlank() -> {
                            dialogTitle = "Missing Account"
                            dialogMessage = "Please enter an account."
                            showDialog = true
                        }

                        entry.username.isBlank() -> {
                            dialogTitle = "Missing Username"
                            dialogMessage = "Please enter a username or email."
                            showDialog = true
                        }

                        entry.password.isBlank() -> {
                            dialogTitle = "Missing Password"
                            dialogMessage = "Please enter a password."
                            showDialog = true
                        }

                        !entry.password.isValidPassword() -> {
                            dialogTitle = "Invalid Password"
                            dialogMessage =
                                "Please enter a valid password. \n\n" + "/**\n" + "  * following password policy:\n" + "  • at least one digit \n" + "  • at least one lowercase alphabet \n" + "  • at least one uppercase alphabet \n" + "  • at least one special character \n" + "  • minimum length of 8 characters\n" + " */"
                            showDialog = true
                        }

                        else -> {
                            onAddEntry(entry)
                            addTitle = ""
                            addUsername = ""
                            addPassword = ""
                            dialogTitle = "Success"
                            dialogMessage = "Account added."
                            showDialog = true
                            showAddSheet = false
                        }
                    }
                }
            )
        }


        if (showDetailSheet && selectedEntry != null) {
            AccountDetailSheet(
                entry = selectedEntry!!,
                onDismiss = { showDetailSheet = false },
                onEdit = { entry ->
                    editTitle = entry.title
                    editUsername = entry.username
                    editPassword = EncryptionUtils.decrypt(entry.password, RSA_PRIVATE_KEY)
                    showDetailSheet = false
                    showEditSheet = true
                },
                onDelete = { entry ->
                    showDetailSheet = false
                    dialogTitle = "Delete Account"
                    dialogMessage = "Are you sure you want to delete “${entry.title}”?"
                    showDialog = true
                }
            )
        }

        if (showEditSheet && selectedEntry != null) {
            AddAccountSheet(
                isFromEdit = true,
                onShowAddSheet = { visible ->
                    showEditSheet = visible
                    if (!visible) {
                        editTitle = ""
                        editUsername = ""
                        editPassword = ""
                    }
                },
                newTitle = editTitle,
                onTitleChange = { editTitle = it },
                newUsername = editUsername,
                onUsernameChange = { editUsername = it },
                newPassword = editPassword,
                onPasswordChange = { editPassword = it },
                onAdd = { entry ->
                    when {
                        entry.title.isBlank() -> {
                            dialogTitle = "Missing Account"
                            dialogMessage = "Please enter an account."
                            showDialog = true
                        }

                        entry.username.isBlank() -> {
                            dialogTitle = "Missing Username"
                            dialogMessage = "Please enter a username or email."
                            showDialog = true
                        }

                        entry.password.isBlank() -> {
                            dialogTitle = "Missing Password"
                            dialogMessage = "Please enter a password."
                            showDialog = true
                        }

                        !entry.password.isValidPassword() -> {
                            dialogTitle = "Invalid Password"
                            dialogMessage =
                                "Please enter a valid password. \n\n" + "/**\n" + "  * following password policy:\n" + "  • at least one digit \n" + "  • at least one lowercase alphabet \n" + "  • at least one uppercase alphabet \n" + "  • at least one special character \n" + "  • minimum length of 8 characters\n" + " */"
                            showDialog = true
                        }

                        else -> {
                            val updatedEntry = entry.copy(id = selectedEntry!!.id)
                            onUpdateEntry(updatedEntry)
                            dialogTitle = "Updated"
                            dialogMessage = "Account updated successfully."
                            showDialog = true
                            showEditSheet = false
                        }
                    }
                }
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(dialogTitle) },
                text = { Text(dialogMessage) },
                confirmButton = {
                    Button(onClick = {
                        showDialog = false
                        if (dialogTitle == "Delete Account") {
                            selectedEntry?.let { onDeleteEntry(it) }
                        }
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    if (dialogTitle.startsWith("Delete")) {
                        OutlinedButton(onClick = { showDialog = false }) {
                            Text("Cancel")
                        }
                    }
                },
                modifier = Modifier
                    .padding(16.dp)
                    .zIndex(100f)
            )
        }
    }
}

