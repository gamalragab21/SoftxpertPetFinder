package net.soft.core.common.data.mapper


internal abstract class Mapper<Dto, Domain, Entity> {

    // dto-domain mappings, mandatory to implement
    abstract fun dtoToDomain(dto: Dto): Domain
    open fun domainToDto(domain: Domain): Dto =
        throw NotImplementedError("override and implement this method")

    // dto-domain list mapping support
    fun dtoToDomain(dtoList: List<Dto>?): List<Domain> = (dtoList ?: emptyList()).map(::dtoToDomain)

    // entity-domain mappings, mandatory to implement
    open fun entityToDomain(entity: Entity): Domain =
        throw NotImplementedError("override and implement this method")

    open fun domainToEntity(domain: Domain): Entity =
        throw NotImplementedError("override and implement this method")

    // entity-domain list mapping support
    fun entityToDomain(domainList: List<Entity>): List<Domain> = domainList.map(::entityToDomain)
    fun domainToEntity(domainList: List<Domain>): List<Entity> = domainList.map(::domainToEntity)
}