package com.ns.spacex.data.local

import androidx.room.*
import com.ns.spacex.model.Rockets

@Database(
    entities = [Rockets::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class RocketDatabase : RoomDatabase() {

    abstract fun getRocketDao(): RocketDao
}
