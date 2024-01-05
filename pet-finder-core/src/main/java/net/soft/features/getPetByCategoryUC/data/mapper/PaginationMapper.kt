package net.soft.features.getPetByCategoryUC.data.mapper

import net.soft.core.common.data.mapper.Mapper
import net.soft.features.getPetByCategoryUC.data.models.dto.PaginationDto
import net.soft.features.getPetByCategoryUC.domain.models.Pagination

internal object PaginationMapper : Mapper<PaginationDto, Pagination, Unit>() {
    override fun dtoToDomain(dto: PaginationDto): Pagination {
        return Pagination(dto.currentPage, dto.totalPages)
    }
}