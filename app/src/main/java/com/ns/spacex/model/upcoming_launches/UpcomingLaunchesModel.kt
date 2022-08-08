package com.ns.spacex.model.upcoming_launches

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpcomingLaunchesModel(
    val id: String?,
    val name: String?,
    @SerializedName("date_utc")
    val date: String?,
    @SerializedName("flight_number")
    val flightNumber: Int?,
    val links: Links

): Serializable {
    override fun hashCode(): Int {
        var result = id.hashCode()
        if(id.isNullOrEmpty()){
            result = 31 * result + id.hashCode()
        }
        return result
    }
}