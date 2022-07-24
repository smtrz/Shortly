package com.tahir.shortlyapp.modules

import android.content.Context
import androidx.room.Room
import com.tahir.shortlyapp.adapters.LinksAdapter
import com.tahir.shortlyapp.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
// Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e.
// everywhere in the application)
// contains all the instances with application context.
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, AppDatabase::class.java, "shortlydb").build()

    @Singleton @Provides fun provideDao(db: AppDatabase) = db.linkDao()

    @Provides
    @Singleton
    fun provideAdapter(@ApplicationContext app: Context): LinksAdapter = LinksAdapter(app)
}
