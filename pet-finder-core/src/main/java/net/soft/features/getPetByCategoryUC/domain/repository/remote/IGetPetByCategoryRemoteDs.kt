package net.soft.features.getPetByCategoryUC.domain.repository.remote

import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.features.getPetByCategoryUC.data.models.dto.PetResponseDto

internal interface IGetPetByCategoryRemoteDs {

    suspend fun getPetsByCategoryName(remoteRequest: RemoteRequest): PetResponseDto
}