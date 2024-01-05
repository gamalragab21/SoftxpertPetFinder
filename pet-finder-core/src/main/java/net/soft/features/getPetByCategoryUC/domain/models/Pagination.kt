package net.soft.features.getPetByCategoryUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pagination(
    var currentPage: Int, var totalPages: Int
) : Parcelable