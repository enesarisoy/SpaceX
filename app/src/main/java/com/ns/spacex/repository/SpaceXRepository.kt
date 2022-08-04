package com.ns.spacex.repository

import com.ns.spacex.api.RetrofitInstance

class SpaceXRepository() {


    suspend fun getAllRockets() = RetrofitInstance.api.getAllRockets()
    suspend fun getRocketDetail(id: String) = RetrofitInstance.api.getRocketDetail(id)
    suspend fun getUpcomingLaunches() = RetrofitInstance.api.getUpcomingLaunches()
    suspend fun getLaunchDetail(id: String) = RetrofitInstance.api.getLaunchDetail(id)




}