package com.ns.spacex.repository

import androidx.room.RoomDatabase
import com.ns.spacex.data.RocketDatabase
import com.ns.spacex.model.Rockets

class RoomRepository(private val db: RocketDatabase) {

    suspend fun upsertRocket(rockets: Rockets) = db.getRocketDao().upsert(rockets)
    fun getSavedRockets() = db.getRocketDao().getAllRockets()
    suspend fun deleteRocket(rockets: Rockets) = db.getRocketDao().deleteRocket(rockets)

}