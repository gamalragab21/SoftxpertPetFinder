package net.soft.features.getPetByCategoryUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    var medium: String, var small: String
) : Parcelable