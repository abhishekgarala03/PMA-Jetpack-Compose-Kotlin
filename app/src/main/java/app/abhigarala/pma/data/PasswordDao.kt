package app.abhigarala.pma.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Insert
    suspend fun insert(entry: PasswordEntry)

    @Query("SELECT * FROM passwords ORDER BY id DESC")
    fun getAll(): Flow<List<PasswordEntry>>
}