package com.ns.spacex.model.upcoming_launches

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UpcomingLaunchesModel(
    val id: String?,
    val name: String?,
    @SerializedName("date_utc")
    val date: String?,
    @SerializedName("flight_number")
    val flightNumber: Int?,
    val links: Links

): Parcelable {

}