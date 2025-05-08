package app.abhigarala.pma.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.abhigarala.pma.data.PasswordEntry

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordManagerScreen(
    entries: List<PasswordEntry>,
    onItemClick: (PasswordEntry) -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Password Manager") },
            colors = TopAppBarDefaults.smallTopAppBarColors()
        )
    }, floatingActionButton = {
        FloatingActionButton(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = "Add password")
        }
    }, content = { paddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding() + 16.dp,
                bottom = paddingValues.calculateBottomPadding() + 16.dp,
                start = 16.dp,
                end = 16.dp
            ), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(entries) { entry ->
                PasswordCard(entry, onClick = { onItemClick(entry) })
            }
        }
    })
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
                    modifier = Modifier.padding(start = 8.dp).padding(top = 4.dp),
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

@Preview(showBackground = true)
@Composable
fun PasswordManagerScreenPreview() {
    val sampleData = remember {
        listOf(
            PasswordEntry("Google", "hunter2"),
            PasswordEntry("LinkedIn", "pa55w0rd"),
            PasswordEntry("Twitter", "qwerty123"),
            PasswordEntry("Facebook", "letmein"),
            PasswordEntry("Instagram", "abcdefg")
        )
    }
    MaterialTheme {
        PasswordManagerScreen(entries = sampleData, onItemClick = {}, onAddClick = {})
    }
}