package app.abhigarala.pma.di

import android.content.Context
import androidx.room.Room
import app.abhigarala.pma.data.AppDatabase
import app.abhigarala.pma.data.PasswordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext app: Context
    ): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "app_db"
    ).build()

    @Provides
    fun providePasswordDao(db: AppDatabase): PasswordDao =
        db.passwordDao()
}