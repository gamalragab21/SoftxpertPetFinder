package net.soft.features.getPetByCategoryUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Colors(
    var primary: String?,
    var secondary: String?,
    var tertiary: String?
) : Parcelable