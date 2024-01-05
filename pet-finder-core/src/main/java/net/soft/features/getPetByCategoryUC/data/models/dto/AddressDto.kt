package net.soft.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

data class AddressDto(
    @SerializedName("address1")
    var address1: Any,
    @SerializedName("address2")
    var address2: Any,
    @SerializedName("city")
    var city: String,
    @SerializedName("country")
    var country: String,
    @SerializedName("postcode")
    var postcode: String,
    @SerializedName("state")
    var state: String
)