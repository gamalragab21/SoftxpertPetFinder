package net.xpert.features.getPetByCategoryUC.data.mapper

import net.xpert.core.common.data.mapper.Mapper
import net.xpert.features.getPetByCategoryUC.data.models.dto.ColorsDto
import net.xpert.features.getPetByCategoryUC.data.models.entity.ColorsEntity
import net.xpert.features.getPetByCategoryUC.domain.models.Colors

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