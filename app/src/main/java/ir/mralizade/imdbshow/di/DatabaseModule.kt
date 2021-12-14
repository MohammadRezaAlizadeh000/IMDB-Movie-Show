package ir.mralizade.imdbshow.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.mralizade.imdbshow.data.database.MoviesDatabase
import ir.mralizade.imdbshow.utils.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MoviesDatabase::class.java,
        DATABASE_NAME
    ).build()


    @Singleton
    @Provides
    fun providePopularMovieDao(database: MoviesDatabase) = database.accessPopularMovieDao()

    @Singleton
    @Provides
    fun provideSingleMovieDao(database: MoviesDatabase) = database.accessSingleMovieDao()

}