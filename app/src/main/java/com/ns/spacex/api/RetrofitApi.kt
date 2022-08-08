package com.ns.spacex.api

import com.ns.spacex.model.Rockets
import com.ns.spacex.model.upcoming_launches.UpcomingLaunchesModel
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {

    @GET("rockets")
    suspend fun getAllRockets(): List<Rockets>

    @GET("rockets/{id}")
    suspend fun getRocketDetail(
        @Path("id") id: String
    ): Rockets

    @GET("launches/upcoming")
    suspend fun getUpcomingLaunches(): List<UpcomingLaunchesModel>

    @GET("launches/{id}")
    suspend fun getLaunchDetail(
        @Path("id") id: String
    ): UpcomingLaunchesModel

}