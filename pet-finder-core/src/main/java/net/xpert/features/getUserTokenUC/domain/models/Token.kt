package net.xpert.features.getUserTokenUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.xpert.core.common.domain.model.BaseDomain

@Parcelize
data class Token(
    var accessToken: String, var expiresIn: Int, var tokenType: String,
) : BaseDomain(), Parcelable {

    fun isInInitialState() = accessToken.isEmpty() || expiresIn <= 0 || tokenType.isEmpty()
    fun getAccessTokenWithType() = "$tokenType $accessToken"

    constructor() : this("", 0, "")
}