package net.soft.features.getUserTokenUC.data.models.entity


internal data class TokenEntity(
    var accessToken: String = "",
    var expiresIn: Int = 0,
    var tokenType: String = "",
    var creationTimeMillis: Long = 0
)