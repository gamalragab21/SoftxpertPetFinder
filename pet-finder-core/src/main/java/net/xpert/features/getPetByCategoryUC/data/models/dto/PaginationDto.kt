package net.xpert.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

data class PaginationDto(
    @SerializedName("current_page")
    var currentPage: Int,
    @SerializedName("total_pages")
    var totalPages: Int
)