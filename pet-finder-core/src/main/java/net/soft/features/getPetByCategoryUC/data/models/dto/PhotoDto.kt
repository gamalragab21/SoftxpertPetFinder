package net.soft.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

internal data class PhotoDto(
    @SerializedName("medium")
    var medium: String,
    @SerializedName("small")
    var small: String
)