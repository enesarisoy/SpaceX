package com.ns.spacex.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
@Entity(tableName = "rockets")
data class Rockets(
    @PrimaryKey(autoGenerate = true)
    var num: Int? = null,
    val name: String?,
    val description: String?,
    val id: String,
    val company: String?,
    @SerializedName("first_flight")
    val firstFlight: String?,
    @SerializedName("flickr_images")
    val flickrImages: ArrayList<String>,
    var isLiked: Boolean
) : Parcelable {
}