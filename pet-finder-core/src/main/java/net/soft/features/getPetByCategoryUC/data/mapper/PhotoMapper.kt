package net.soft.features.getPetByCategoryUC.data.mapper

import net.soft.core.common.data.mapper.Mapper
import net.soft.features.getPetByCategoryUC.data.models.dto.PhotoDto
import net.soft.features.getPetByCategoryUC.data.models.entity.PhotoEntity
import net.soft.features.getPetByCategoryUC.domain.models.Photo

internal object PhotoMapper : Mapper<PhotoDto, Photo, PhotoEntity>() {
    override fun dtoToDomain(dto: PhotoDto): Photo {
        return Photo(dto.medium, dto.small)
    }

    override fun domainToEntity(domain: Photo): PhotoEntity {
        return PhotoEntity(domain.medium, domain.small)
    }

    override fun entityToDomain(entity: PhotoEntity): Photo {
        return Photo(entity.fileMediumPath, entity.fileSmallPath)
    }
}