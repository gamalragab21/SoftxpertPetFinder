package net.xpert.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName
import net.xpert.core.common.data.model.dto.BaseDto

internal data class PetResponseDto(
    @SerializedName("animals")
    var animals: List<AnimalDto>
) : BaseDto()