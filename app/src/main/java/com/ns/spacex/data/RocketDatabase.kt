package com.ns.spacex.data

import android.content.Context
import androidx.room.*
import com.ns.spacex.model.Rockets

@Database(
    entities = [Rockets::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RocketDatabase : RoomDatabase() {

    abstract fun getRocketDao(): RocketDao

    companion object {
        @Volatile   //if instance changes, other threads will immediately see this change
        private var instance: RocketDatabase? = null
        private val LOCK = Any()

        //whenever create an instance of database invoke fun gonna called
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RocketDatabase::class.java,
                "rocket_db.db"
            ).build()
    }
}
