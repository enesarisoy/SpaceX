package com.ns.spacex.api

import com.ns.spacex.model.Rockets
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {

    @GET("rockets")
    suspend fun getAllRockets(): List<Rockets>

    @GET("rockets/{id}")
    suspend fun getRocketDetail(
        @Path("id") id: String
    ): Rockets
}