package net.xpert.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

internal data class ColorsDto(
    @SerializedName("primary")
    var primary: String?=null,
    @SerializedName("secondary")
    var secondary: String?=null,
    @SerializedName("tertiary")
    var tertiary: String?=null
)