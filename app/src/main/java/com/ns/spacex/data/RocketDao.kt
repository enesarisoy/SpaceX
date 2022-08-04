package com.ns.spacex.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ns.spacex.model.Rockets

@Dao
interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(rockets: Rockets): Long

    @Query("SELECT * FROM rockets")
    fun getAllRockets(): LiveData<List<Rockets>>

    @Delete
    suspend fun deleteRocket(rockets: Rockets)
}