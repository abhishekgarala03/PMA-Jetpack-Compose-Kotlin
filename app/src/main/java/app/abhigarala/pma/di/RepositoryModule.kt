package app.abhigarala.pma.di

import app.abhigarala.pma.data.PasswordRepository
import app.abhigarala.pma.data.PasswordRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPasswordRepository(
        impl: PasswordRepositoryImpl
    ): PasswordRepository
}