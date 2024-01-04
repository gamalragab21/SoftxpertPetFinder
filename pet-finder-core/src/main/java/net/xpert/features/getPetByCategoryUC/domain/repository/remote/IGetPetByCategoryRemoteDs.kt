package net.xpert.features.getPetByCategoryUC.domain.repository.remote

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.features.getPetByCategoryUC.data.models.dto.PetResponseDto

internal interface IGetPetByCategoryRemoteDs {

    suspend fun getPetsByCategoryName(remoteRequest: RemoteRequest): PetResponseDto
}