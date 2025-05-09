package app.abhigarala.pma.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.abhigarala.pma.data.PasswordEntry
import app.abhigarala.pma.data.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(
    private val repo: PasswordRepository
) : ViewModel() {

    val entries: StateFlow<List<PasswordEntry>> =
        repo.observeAll()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                emptyList()
            )

    fun addEntry(entry: PasswordEntry) = viewModelScope.launch {
        repo.add(entry)
    }

    fun updateEntry(entry: PasswordEntry) = viewModelScope.launch {
        repo.edit(entry)
    }

    fun deleteEntry(entry: PasswordEntry) = viewModelScope.launch {
        repo.remove(entry)
    }
}