package net.xpert.features.getPetByCategoryUC.data.mapper

import com.google.gson.reflect.TypeToken
import net.xpert.core.common.data.mapper.Mapper
import net.xpert.features.getPetByCategoryUC.data.models.dto.AnimalDto
import net.xpert.features.getPetByCategoryUC.data.models.entity.AnimalEntity
import net.xpert.features.getPetByCategoryUC.data.models.entity.PhotoEntity
import net.xpert.features.getPetByCategoryUC.domain.models.Animal
import java.lang.reflect.Type

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
        val type: Type by lazy { object : TypeToken<ArrayList<PhotoEntity>>() {}.type }

        return Animal(
            ColorsMapper.entityToDomain(entity.colors),
            entity.age, entity.gender, entity.id, entity.name,
            PhotoMapper.entityToDomain(entity.photos),
            entity.type, entity.address, entity.size, entity.url
        )
    }
}