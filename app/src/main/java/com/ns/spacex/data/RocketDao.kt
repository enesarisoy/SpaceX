package com.ns.spacex.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ns.spacex.model.Rockets

@Dao
interface RocketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(rockets: Rockets): Long

    @Delete
    suspend fun deleteRocket(rockets: Rockets)

    @Query("DELETE FROM rockets WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM rockets")
    fun getAllRockets(): LiveData<List<Rockets>>

    @Query("SELECT EXISTS (SELECT 1 FROM rockets WHERE id = :id)")
    fun checkFavorite(id: String): Boolean
}