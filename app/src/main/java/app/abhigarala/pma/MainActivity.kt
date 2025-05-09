package app.abhigarala.pma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import app.abhigarala.pma.PMApp.Companion.RSA_PRIVATE_KEY
import app.abhigarala.pma.cypher.EncryptionUtils
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.ui.screen.PasswordManagerScreen
import app.abhigarala.pma.ui.theme.PMATheme
import app.abhigarala.pma.viewmodel.PasswordViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: PasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val entries by viewModel.entries.collectAsState()
                    PasswordManagerScreen(
                        entries = entries,
                        onAddEntry = { entry ->
                            val encryptedPassword = EncryptionUtils.encrypt(
                                entry.password,
                                PMApp.RSA_PUBLIC_KEY
                            )
                            viewModel.addEntry(
                                PasswordEntry(
                                    title = entry.title,
                                    username = entry.username,
                                    password = encryptedPassword
                                )
                            )
                        },
                        onUpdateEntry = { entry ->
                            val encryptedPassword = EncryptionUtils.encrypt(
                                entry.password,
                                PMApp.RSA_PUBLIC_KEY
                            )
                            viewModel.updateEntry(
                                PasswordEntry(
                                    id = entry.id,
                                    title = entry.title,
                                    username = entry.username,
                                    password = encryptedPassword
                                )
                            )
                        },
                        onDeleteEntry = { entry ->
                            viewModel.deleteEntry(
                                PasswordEntry(
                                    id = entry.id,
                                    title = entry.title,
                                    username = entry.username,
                                    password = entry.password
                                )
                            )
                        },
                        decryptPassword = {
                            EncryptionUtils.decrypt(it, RSA_PRIVATE_KEY)
                        }
                    )
                }
            }
        }
    }
}