package net.xpert.features.getUserTokenUC.data.models.dto


import com.google.gson.annotations.SerializedName
import net.xpert.core.common.data.model.dto.BaseDto

internal data class TokenDto(
    @SerializedName("access_token")
    var accessToken: String,
    @SerializedName("expires_in")
    var expiresIn: Int,
    @SerializedName("token_type")
    var tokenType: String
) : BaseDto()