package net.soft.features.getPetByCategoryUC.data.mapper

import net.soft.core.common.data.mapper.Mapper
import net.soft.features.getPetByCategoryUC.data.models.dto.ColorsDto
import net.soft.features.getPetByCategoryUC.data.models.entity.ColorsEntity
import net.soft.features.getPetByCategoryUC.domain.models.Colors

internal object ColorsMapper : Mapper<ColorsDto, Colors, ColorsEntity>() {
    override fun dtoToDomain(dto: ColorsDto): Colors {
        return Colors(dto.primary, dto.secondary, dto.tertiary)
    }

    override fun domainToEntity(domain: Colors): ColorsEntity {
        return ColorsEntity(domain.primary, domain.secondary, domain.tertiary)
    }

    override fun entityToDomain(entity: ColorsEntity): Colors {
        return Colors(entity.primary, entity.secondary, entity.tertiary)
    }
}