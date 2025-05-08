package app.abhigarala.pma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.data.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repo: PasswordRepository
) : ViewModel() {

    // Expose the list of entries as UI state
    val entries = repo.observeAll()
        .map { list -> list.map { it.toDomain() } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    /** Called from UI to add a new entry */
    fun addEntry(title: String, username: String, password: String) {
        viewModelScope.launch {
            val entity = PasswordEntry(
                title = title,
                username = username,
                password = password
            )
            repo.add(entity)
        }
    }

    // Optional: helper to convert Entity → UI model
    private fun PasswordEntry.toDomain() =
        PasswordEntry(title = title, username = username, password = password)
}