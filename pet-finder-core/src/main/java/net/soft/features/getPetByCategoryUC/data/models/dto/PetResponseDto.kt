package net.soft.features.getPetByCategoryUC.data.models.dto


import com.google.gson.annotations.SerializedName
import net.soft.core.common.data.model.dto.BaseDto

internal data class PetResponseDto(
    @SerializedName("animals")
    var animals: List<AnimalDto>,
    @SerializedName("pagination")
    var pagination: PaginationDto
) : BaseDto()