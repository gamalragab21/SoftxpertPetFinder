package net.soft.features.getPetByCategoryUC.data.repository.remote

import net.soft.core.common.domain.model.request.RemoteRequest
import net.soft.core.common.domain.repository.remote.INetworkProvider
import net.soft.features.getPetByCategoryUC.data.models.dto.PetResponseDto
import net.soft.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
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