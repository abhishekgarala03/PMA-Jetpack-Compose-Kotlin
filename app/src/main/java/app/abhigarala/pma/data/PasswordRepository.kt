package app.abhigarala.pma.data

import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    suspend fun add(entry: PasswordEntry)
    fun observeAll(): Flow<List<PasswordEntry>>
}