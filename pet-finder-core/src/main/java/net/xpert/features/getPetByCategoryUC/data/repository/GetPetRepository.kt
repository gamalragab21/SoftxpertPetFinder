package net.xpert.features.getPetByCategoryUC.data.repository

import net.xpert.core.common.domain.model.request.RemoteRequest
import net.xpert.features.getPetByCategoryUC.data.mapper.AnimalMapper
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import net.xpert.features.getPetByCategoryUC.domain.repository.IGetPetRepository
import net.xpert.features.getPetByCategoryUC.domain.repository.local.IGetPetByCategoryLocalDs
import net.xpert.features.getPetByCategoryUC.domain.repository.remote.IGetPetByCategoryRemoteDs
import net.xpert.features.getPetTypes.domain.enums.PetType
import javax.inject.Inject

internal class GetPetRepository @Inject constructor(
    private val localDs: IGetPetByCategoryLocalDs, private val remoteDs: IGetPetByCategoryRemoteDs
) : IGetPetRepository {
    override suspend fun getPetsByCategoryNameFromRemote(remoteRequest: RemoteRequest): List<Animal> {
        val result = remoteDs.getPetsByCategoryName(remoteRequest)
        return AnimalMapper.dtoToDomain(result.animals)
    }

    override suspend fun getPetsByCategoryNameFromLocal(petType: PetType): List<Animal> {
        val result = localDs.getAnimals(petType)
        return AnimalMapper.entityToDomain(result)
    }

    override suspend fun saveAnimals(animals: List<Animal>) {
        val result = AnimalMapper.domainToEntity(animals)
        localDs.insertAnimals(result)
    }
}