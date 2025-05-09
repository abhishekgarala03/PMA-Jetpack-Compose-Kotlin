package app.abhigarala.pma.data

import kotlinx.coroutines.flow.Flow

interface PasswordRepository {
    fun observeAll(): Flow<List<PasswordEntry>>
    suspend fun add(entry: PasswordEntry)
    suspend fun edit(entry: PasswordEntry)
    suspend fun remove(entry: PasswordEntry)
}