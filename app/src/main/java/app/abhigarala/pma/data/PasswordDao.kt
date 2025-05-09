package app.abhigarala.pma.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {
    @Query("SELECT * FROM passwords ORDER BY id DESC")
    fun getAll(): Flow<List<PasswordEntry>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(entry: PasswordEntry)

    @Update
    suspend fun update(entry: PasswordEntry)

    @Delete
    suspend fun delete(entry: PasswordEntry)
}