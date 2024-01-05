package net.xpert.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

data class ContactDto(
    @SerializedName("address")
    var address: AddressDto,
    @SerializedName("email")
    var email: String,
    @SerializedName("phone")
    var phone: Any
)