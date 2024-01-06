package net.soft.features.getPetByCategoryUC.data.models.dto


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
    private var photos: List<PhotoDto>,
    @SerializedName("type")
    var type: String,
    @SerializedName("contact")
    var contact: ContactDto,
    @SerializedName("size")
    var size: String,
    @SerializedName("url")
    var url: String
) {
    internal fun getFirstPhoto(): PhotoDto {
        return if (photos.isEmpty()) PhotoDto("", "")
        else photos.first()
    }

    internal fun getPetAddress(): String = with(contact.address) {
        return city.plus(",").plus(state).plus(",").plus(country)
    }
}