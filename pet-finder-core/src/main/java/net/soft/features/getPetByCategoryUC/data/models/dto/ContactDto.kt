package net.soft.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName

data class ContactDto(
    @SerializedName("address")
    var address: AddressDto
)