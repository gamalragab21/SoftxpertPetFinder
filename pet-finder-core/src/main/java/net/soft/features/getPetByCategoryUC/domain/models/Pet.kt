package net.soft.features.getPetByCategoryUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pet(
    val animals: List<Animal>, val pagination: Pagination
) : Parcelable