package com.ns.spacex.di

import android.content.Context
import androidx.room.Room
import com.ns.spacex.data.local.RocketDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): RocketDatabase {
        return Room.databaseBuilder(
            appContext,
            RocketDatabase::class.java,
            "rocket_db.db"
        ).fallbackToDestructiveMigration().build()
    }
}