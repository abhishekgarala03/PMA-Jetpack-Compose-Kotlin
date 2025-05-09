package app.abhigarala.pma.di

import android.content.Context
import app.abhigarala.pma.data.PasswordRepository
import app.abhigarala.pma.data.PasswordRepositoryImpl
import app.abhigarala.pma.sharedpref.DataStoreManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}