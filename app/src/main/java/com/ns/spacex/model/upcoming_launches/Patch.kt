package com.ns.spacex.model.upcoming_launches

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patch(
    val small: String?
): Parcelable {
}