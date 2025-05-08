package app.abhigarala.pma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.ui.screen.PasswordManagerScreen
import app.abhigarala.pma.ui.theme.PMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PMATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val sampleData = remember {
                        listOf(
                            PasswordEntry("Google", "hunter2"),
                            PasswordEntry("LinkedIn", "pa55w0rd"),
                            PasswordEntry("Twitter", "qwerty123"),
                            PasswordEntry("Facebook", "letmein"),
                            PasswordEntry("Instagram", "abcdefg"),
                            PasswordEntry("Google", "hunter2"),
                            PasswordEntry("LinkedIn", "pa55w0rd"),
                            PasswordEntry("Twitter", "qwerty123"),
                            PasswordEntry("Facebook", "letmein"),
                            PasswordEntry("Instagram", "abcdefg"),
                            PasswordEntry("Google", "hunter2"),
                            PasswordEntry("LinkedIn", "pa55w0rd"),
                            PasswordEntry("Twitter", "qwerty123"),
                            PasswordEntry("Facebook", "letmein"),
                            PasswordEntry("Instagram", "abcdefg")
                        )
                    }

                    PasswordManagerScreen(entries = sampleData, onItemClick = {}, onAddClick = {})
                }
            }
        }
    }
}