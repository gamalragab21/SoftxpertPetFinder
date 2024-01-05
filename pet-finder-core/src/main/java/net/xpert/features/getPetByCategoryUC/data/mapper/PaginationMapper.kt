package net.xpert.features.getPetByCategoryUC.data.mapper

import net.xpert.core.common.data.mapper.Mapper
import net.xpert.features.getPetByCategoryUC.data.models.dto.PaginationDto
import net.xpert.features.getPetByCategoryUC.domain.models.Pagination

internal object PaginationMapper : Mapper<PaginationDto, Pagination, Unit>() {
    override fun dtoToDomain(dto: PaginationDto): Pagination {
        return Pagination(dto.currentPage,dto.totalPages)
    }
}