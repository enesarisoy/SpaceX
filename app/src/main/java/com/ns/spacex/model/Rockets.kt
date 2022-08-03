package com.ns.spacex.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

//@Parcelize
data class Rockets(
    val name: String?,
    val description: String?,
    val id: String,
    val company: String?,
    @SerializedName("first_flight")
    val firstFlight: String?,
    @SerializedName("flickr_images")
    val flickrImages: List<String>
): Serializable {
    override fun hashCode(): Int {
        var result = id.hashCode()
        if(id.isNullOrEmpty()){
            result = 31 * result + id.hashCode()
        }
        return result
    }
}