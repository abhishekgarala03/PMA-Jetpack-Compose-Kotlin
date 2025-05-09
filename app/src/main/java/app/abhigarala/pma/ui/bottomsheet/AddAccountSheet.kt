package app.abhigarala.pma.ui.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.R
import app.abhigarala.pma.data.PasswordEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountSheet(
    state: AccountInputState,
    onDismiss: () -> Unit,
    onInputChange: (field: InputField, value: String) -> Unit,
    onSubmit: (PasswordEntry) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        shape = MaterialTheme.shapes.large
    ) {
        AccountInputContent(
            state = state,
            onInputChange = onInputChange,
            onSubmit = onSubmit,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
private fun AccountInputContent(
    state: AccountInputState,
    onInputChange: (field: InputField, value: String) -> Unit,
    onSubmit: (PasswordEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        AccountInputFields(
            state = state,
            onInputChange = onInputChange
        )

        SubmitButton(
            isEditMode = state.isEditMode,
            onSubmit = {
                onSubmit(
                    PasswordEntry(
                        title = state.title,
                        username = state.username,
                        password = state.password
                    )
                )
            }
        )
    }
}

@Composable
private fun AccountInputFields(
    state: AccountInputState,
    onInputChange: (field: InputField, value: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AccountInputField(
            label = stringResource(R.string.account),
            value = state.title,
            onValueChange = { onInputChange(InputField.TITLE, it) }
        )

        AccountInputField(
            label = stringResource(R.string.username),
            value = state.username,
            onValueChange = { onInputChange(InputField.USERNAME, it) }
        )

        AccountInputField(
            label = stringResource(R.string.password),
            value = state.password,
            onValueChange = { onInputChange(InputField.PASSWORD, it) },
            isPassword = true
        )
    }
}

@Composable
private fun AccountInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun SubmitButton(
    isEditMode: Boolean,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onSubmit,
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = if (isEditMode) stringResource(R.string.update_account) else stringResource(R.string.add_new_account))
    }
}

data class AccountInputState(
    val title: String = "",
    val username: String = "",
    val password: String = "",
    val isEditMode: Boolean = false
)

sealed class InputField {
    object TITLE : InputField()
    object USERNAME : InputField()
    object PASSWORD : InputField()
}