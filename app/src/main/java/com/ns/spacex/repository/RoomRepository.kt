package com.ns.spacex.repository

import com.ns.spacex.data.local.RocketDatabase
import com.ns.spacex.model.Rockets
import javax.inject.Inject

class RoomRepository @Inject constructor(db: RocketDatabase) {

    private val dao = db.getRocketDao()

    suspend fun upsertRocket(rockets: Rockets) = dao.upsert(rockets)
    suspend fun deleteById(id: String) = dao.deleteById(id)
    suspend fun deleteRocket(rockets: Rockets) = dao.deleteRocket(rockets)

    fun getSavedRockets() = dao.getAllRockets()
    fun checkFavorite(id: String) = dao.checkFavorite(id)

}