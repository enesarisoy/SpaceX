package com.ns.spacex.repository

import com.ns.spacex.data.RocketDao
import com.ns.spacex.model.Rockets

class RoomRepository(private val dao: RocketDao) {

    suspend fun upsertRocket(rockets: Rockets) = dao.upsert(rockets)
    fun getSavedRockets() = dao.getAllRockets()
    suspend fun deleteRocket(rockets: Rockets) = dao.deleteRocket(rockets)
    fun checkFavorite(id: String, rockets: Rockets) = dao.checkFavorite(id)

}