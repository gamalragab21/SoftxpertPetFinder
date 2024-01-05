package net.xpert.features.getUserTokenUC.data.mapper

import net.xpert.core.common.data.mapper.Mapper
import net.xpert.features.getUserTokenUC.data.models.dto.TokenDto
import net.xpert.features.getUserTokenUC.data.models.entity.TokenEntity
import net.xpert.features.getUserTokenUC.domain.models.Token

internal object UserTokenMapper : Mapper<TokenDto, Token, TokenEntity>() {
    override fun dtoToDomain(dto: TokenDto): Token {
        return Token(dto.accessToken, dto.expiresIn, dto.tokenType, System.currentTimeMillis())
    }

    override fun domainToDto(domain: Token): TokenDto {
        return TokenDto(domain.accessToken, domain.expiresIn, domain.tokenType)
    }

    override fun domainToEntity(domain: Token): TokenEntity {
        return TokenEntity(
            domain.accessToken,
            domain.expiresIn,
            domain.tokenType,
            domain.creationTimeMillis
        )
    }

    override fun entityToDomain(entity: TokenEntity): Token {
        return Token(
            entity.accessToken,
            entity.expiresIn,
            entity.tokenType,
            entity.creationTimeMillis
        )
    }
}