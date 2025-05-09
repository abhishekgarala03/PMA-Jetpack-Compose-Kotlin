package app.abhigarala.pma.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class PasswordRepositoryImpl @Inject constructor(
    private val dao: PasswordDao
) : PasswordRepository {
    override fun observeAll(): Flow<List<PasswordEntry>> =
        dao.getAll()

    override suspend fun add(entry: PasswordEntry) {
        dao.insert(entry)
    }

    override suspend fun edit(entry: PasswordEntry) {
        dao.update(entry)
    }

    override suspend fun remove(entry: PasswordEntry) {
        dao.delete(entry)
    }
}