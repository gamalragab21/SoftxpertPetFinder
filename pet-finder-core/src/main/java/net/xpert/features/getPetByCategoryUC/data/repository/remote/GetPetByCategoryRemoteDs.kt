package net.xpert.features.getPetByCategoryUC.data.repository.remote

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.core.common.domain.repository.remote.INetworkProvider
import net.xpert.features.getPetByCategoryUC.data.models.dto.PetResponseDto
import net.xpert.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
import javax.inject.Inject

internal class GetPetByCategoryRemoteDs @Inject constructor(private val networkProvider: INetworkProvider) :
    IGetPetByCategoryRemoteDs {
    override suspend fun getPetsByCategoryName(remoteRequest: RemoteRequest): PetResponseDto {
        return networkProvider.get(
            PetResponseDto::class.java,
            pathUrl = petByCategory,
            queryParams = remoteRequest.requestQueries
        )
    }

    companion object {
        const val petByCategory = "animals"
    }
}