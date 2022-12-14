package com.ns.spacex.repository

import com.ns.spacex.data.remote.RetrofitApi
import javax.inject.Inject

class SpaceXRepository @Inject constructor(private val retrofitApi: RetrofitApi) {

    suspend fun getAllRockets() = retrofitApi.getAllRockets()
    suspend fun getRocketDetail(id: String) = retrofitApi.getRocketDetail(id)
    suspend fun getUpcomingLaunches() = retrofitApi.getUpcomingLaunches()
    suspend fun getLaunchDetail(id: String) = retrofitApi.getLaunchDetail(id)

}