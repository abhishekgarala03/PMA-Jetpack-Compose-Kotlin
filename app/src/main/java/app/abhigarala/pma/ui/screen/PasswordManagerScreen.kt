package app.abhigarala.pma.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.ui.bottomsheet.AccountDetailSheet
import app.abhigarala.pma.ui.bottomsheet.AccountInputState
import app.abhigarala.pma.ui.bottomsheet.AddAccountSheet
import app.abhigarala.pma.ui.bottomsheet.InputField
import app.abhigarala.pma.ui.itemlayout.PasswordCard
import app.abhigarala.pma.utils.isValidPassword

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(
    entries: List<PasswordEntry>,
    onAddEntry: (PasswordEntry) -> Unit,
    onUpdateEntry: (PasswordEntry) -> Unit,
    onDeleteEntry: (PasswordEntry) -> Unit,
    decryptPassword: (String) -> String
) {
    var state by remember { mutableStateOf(PasswordManagerState()) }

    ScaffoldManager(
        entries = entries,
        state = state,
        onStateChange = { state = it },
        onAddEntry = onAddEntry,
        onUpdateEntry = onUpdateEntry,
        onDeleteEntry = onDeleteEntry,
        decryptPassword = decryptPassword
    )
}

@Composable
private fun ScaffoldManager(
    entries: List<PasswordEntry>,
    state: PasswordManagerState,
    onStateChange: (PasswordManagerState) -> Unit,
    onAddEntry: (PasswordEntry) -> Unit,
    onUpdateEntry: (PasswordEntry) -> Unit,
    onDeleteEntry: (PasswordEntry) -> Unit,
    decryptPassword: (String) -> String
) {
    Box {
        MainContent(
            entries = entries,
            onEntrySelected = { entry ->
                onStateChange(state.copy(screenState = ScreenState.Detail(entry)))
            },
            onAddClick = {
                onStateChange(
                    state.copy(
                        screenState = ScreenState.Add,
                        inputState = AccountInputState()
                    )
                )
            }
        )

        when (val screenState = state.screenState) {
            is ScreenState.Add -> AddAccountSheet(
                state = state.inputState,
                onDismiss = { onStateChange(state.copy(screenState = ScreenState.None)) },
                onInputChange = { field, value ->
                    onStateChange(
                        state.copy(
                            inputState = updateInputState(
                                field,
                                value,
                                state.inputState
                            )
                        )
                    )
                },
                onSubmit = { handleSubmit(it, state, onAddEntry, onUpdateEntry, onStateChange) }
            )

            is ScreenState.Edit -> AddAccountSheet(
                state = state.inputState,
                onDismiss = { onStateChange(state.copy(screenState = ScreenState.None)) },
                onInputChange = { field, value ->
                    onStateChange(
                        state.copy(
                            inputState = updateInputState(
                                field,
                                value,
                                state.inputState
                            )
                        )
                    )
                },
                onSubmit = { handleSubmit(it, state, onAddEntry, onUpdateEntry, onStateChange) }
            )

            is ScreenState.Detail -> AccountDetailSheet(
                entry = screenState.entry,
                onDismiss = { onStateChange(state.copy(screenState = ScreenState.None)) },
                onEdit = { entry ->
                    onStateChange(
                        state.copy(
                            screenState = ScreenState.Edit(entry),
                            inputState = AccountInputState(
                                title = entry.title,
                                username = entry.username,
                                password = decryptPassword(entry.password),
                                isEditMode = true
                            )
                        )
                    )
                },
                onDelete = { entry ->
                    onStateChange(
                        state.copy(
                            dialogState = DialogState.ConfirmDelete(entry),
                            screenState = ScreenState.Detail(entry)
                        )
                    )
                },
                decryptPassword = decryptPassword
            )

            ScreenState.None -> Unit
        }

        DialogManager(
            state = state.dialogState,
            onDismiss = { onStateChange(state.copy(dialogState = DialogState.Hidden)) },
            onConfirmDelete = { entry ->
                onDeleteEntry(entry)
                onStateChange(state.copy(dialogState = DialogState.Hidden))
            },
            onStateChange = onStateChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainContent(
    entries: List<PasswordEntry>,
    onEntrySelected: (PasswordEntry) -> Unit,
    onAddClick: () -> Unit
) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Password Manager") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { padding ->
        PasswordEntryList(
            entries = entries,
            onEntrySelected = onEntrySelected,
            padding = padding
        )
    }
}

@Composable
private fun PasswordEntryList(
    entries: List<PasswordEntry>,
    onEntrySelected: (PasswordEntry) -> Unit,
    padding: PaddingValues
) {
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
            PasswordCard(entry = entry) { onEntrySelected(entry) }
        }
    }
}

@Composable
private fun DialogManager(
    state: DialogState,
    onDismiss: () -> Unit,
    onConfirmDelete: (PasswordEntry) -> Unit,
    onStateChange: (PasswordManagerState) -> Unit
) {
    when (state) {
        is DialogState.Alert -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(state.title) },
            text = { Text(state.message) },
            confirmButton = {
                Button(onClick = onDismiss) {
                    Text("OK")
                }
            }
        )

        is DialogState.ConfirmDelete -> AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Delete Account") },
            text = { Text("Are you sure you want to delete “${state.entry.title}”?") },
            confirmButton = {
                Button(onClick = {
                    onConfirmDelete(state.entry)
                    onStateChange(
                        PasswordManagerState(
                            screenState = ScreenState.None,
                            dialogState = DialogState.Hidden
                        )
                    )
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )

        DialogState.Hidden -> Unit
    }
}

private fun updateInputState(
    field: InputField,
    value: String,
    currentState: AccountInputState
): AccountInputState {
    return when (field) {
        InputField.TITLE -> currentState.copy(title = value)
        InputField.USERNAME -> currentState.copy(username = value)
        InputField.PASSWORD -> currentState.copy(password = value)
    }
}

private fun handleSubmit(
    entry: PasswordEntry,
    state: PasswordManagerState,
    onAddEntry: (PasswordEntry) -> Unit,
    onUpdateEntry: (PasswordEntry) -> Unit,
    onStateChange: (PasswordManagerState) -> Unit
) {
    when (val validation = validateEntry(entry)) {
        is ValidationResult.Valid -> {
            if (state.inputState.isEditMode) {
                onUpdateEntry(entry.copy(id = (state.screenState as ScreenState.Edit).entry.id))
                onStateChange(
                    state.copy(
                        screenState = ScreenState.None,
                        dialogState = DialogState.Alert("Updated", "Account updated successfully.")
                    )
                )
            } else {
                onAddEntry(entry)
                onStateChange(
                    state.copy(
                        screenState = ScreenState.None,
                        dialogState = DialogState.Alert("Success", "Account added.")
                    )
                )
            }
        }

        is ValidationResult.Invalid -> {
            onStateChange(
                state.copy(
                    dialogState = DialogState.Alert(validation.title, validation.message)
                )
            )
        }
    }
}

sealed class ValidationResult {
    data class Valid(val entry: PasswordEntry) : ValidationResult()
    data class Invalid(val title: String, val message: String) : ValidationResult()
}

private fun validateEntry(entry: PasswordEntry): ValidationResult {
    return when {
        entry.title.isBlank() -> ValidationResult.Invalid(
            "Missing Account",
            "Please enter an account."
        )

        entry.username.isBlank() -> ValidationResult.Invalid(
            "Missing Username",
            "Please enter a username or email."
        )

        entry.password.isBlank() -> ValidationResult.Invalid(
            "Missing Password",
            "Please enter a password."
        )

        !entry.password.isValidPassword() -> ValidationResult.Invalid(
            "Invalid Password",
            "Password must contain:\n" +
                    "• 1 digit\n• 1 lowercase\n• 1 uppercase\n" +
                    "• 1 special character\n• Minimum 12 characters"
        )

        else -> ValidationResult.Valid(entry)
    }
}

sealed class ScreenState {
    object None : ScreenState()
    object Add : ScreenState()
    data class Edit(val entry: PasswordEntry) : ScreenState()
    data class Detail(val entry: PasswordEntry) : ScreenState()
}

sealed class DialogState {
    object Hidden : DialogState()
    data class Alert(val title: String, val message: String) : DialogState()
    data class ConfirmDelete(val entry: PasswordEntry) : DialogState()
}

data class PasswordManagerState(
    val screenState: ScreenState = ScreenState.None,
    val dialogState: DialogState = DialogState.Hidden,
    val inputState: AccountInputState = AccountInputState()
)

