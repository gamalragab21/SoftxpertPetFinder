package net.soft.features.getPetByCategoryUC.data.mapper

import net.soft.core.common.data.mapper.Mapper
import net.soft.features.getPetByCategoryUC.data.models.dto.AnimalDto
import net.soft.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.soft.features.getPetByCategoryUC.domain.models.Animal

internal object AnimalMapper : Mapper<AnimalDto, Animal, AnimalEntity>() {
    override fun dtoToDomain(dto: AnimalDto): Animal {
        return Animal(
            ColorsMapper.dtoToDomain(dto.colors),
            dto.age, dto.gender, dto.id, dto.name,
            PhotoMapper.dtoToDomain(dto.getFirstPhoto()),
            dto.type, dto.getPetAddress(),
            dto.size, dto.url
        )
    }

    override fun domainToEntity(domain: Animal): AnimalEntity {
        return AnimalEntity(
            ColorsMapper.domainToEntity(domain.colors),
            domain.age, domain.gender, domain.id, domain.name,
            PhotoMapper.domainToEntity(domain.photos), domain.type,
            domain.address, domain.size, domain.url
        )
    }

    override fun entityToDomain(entity: AnimalEntity): Animal {
        return Animal(
            ColorsMapper.entityToDomain(entity.colors),
            entity.age, entity.gender, entity.id, entity.name,
            PhotoMapper.entityToDomain(entity.photos),
            entity.type, entity.address, entity.size, entity.url
        )
    }
}