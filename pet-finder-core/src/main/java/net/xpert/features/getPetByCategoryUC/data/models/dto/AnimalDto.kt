package net.xpert.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

internal data class AnimalDto(
    @SerializedName("age")
    var age: String,
    @SerializedName("colors")
    var colors: ColorsDto,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("photos")
    var photos: List<PhotoDto>,
    @SerializedName("type")
    var type: String,
    @SerializedName("url")
    var url: String,
)