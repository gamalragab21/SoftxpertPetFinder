package net.xpert.core.common.data.mapper

import net.xpert.core.common.data.model.dto.BaseDto
import net.xpert.core.common.domain.model.BaseDomain


internal abstract class Mapper<Dto, Domain, Entity> {

    // dto-domain mappings, mandatory to implement
    abstract fun dtoToDomain(dto: Dto): Domain
    open fun domainToDto(domain: Domain): Dto =
        throw NotImplementedError("override and implement this method")

    // dto-domain list mapping support
    fun dtoToDomain(dtoList: List<Dto>?): List<Domain> = (dtoList ?: emptyList()).map(::dtoToDomain)
    fun domainToDto(domainList: List<Domain>?): List<Dto> =
        (domainList ?: emptyList()).map(::domainToDto)

    // entity-domain mappings, mandatory to implement
    open fun entityToDomain(entity: Entity): Domain =
        throw NotImplementedError("override and implement this method")

    open fun domainToEntity(domain: Domain): Entity =
        throw NotImplementedError("override and implement this method")

    // entity-domain list mapping support
    fun entityToDomain(domainList: List<Entity>): List<Domain> = domainList.map(::entityToDomain)
    fun domainToEntity(domainList: List<Domain>): List<Entity> = domainList.map(::domainToEntity)

    // entity-dto mappings, mandatory to implement
    open fun entityToDto(entity: Entity): Dto =
        throw NotImplementedError("override and implement this method")

    open fun dtoToEntity(dto: Dto): Entity =
        throw NotImplementedError("override and implement this method")

    // entity-dto list mapping support
}

internal fun BaseDto.toBaseDomain(): BaseDomain = BaseDomain(
    message = message ?: "",
    codeDto = code ?: -1
)

internal fun BaseDomain.toBaseDto(): BaseDto = BaseDto(
    message = message,
    code = codeDto
)
