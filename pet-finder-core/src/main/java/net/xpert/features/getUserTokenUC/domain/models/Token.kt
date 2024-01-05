package net.xpert.features.getUserTokenUC.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import net.xpert.core.common.domain.model.BaseDomain

@Parcelize
data class Token(
    var accessToken: String, var expiresIn: Int, var tokenType: String, var creationTimeMillis: Long
) : BaseDomain(), Parcelable {

    fun isInInitialState() =
        accessToken.isEmpty() || expiresIn <= 0 || tokenType.isEmpty() || creationTimeMillis <= 0

    fun isAccessTokenExpired(): Boolean {
        return if (isInInitialState())
            true
        else {
            val currentTimeMillis = System.currentTimeMillis()
            // Calculate the elapsed time in seconds
            val elapsedTimeInSeconds = ((currentTimeMillis - creationTimeMillis) / 1000).toInt()
            // Check if the elapsed time is greater than the expiresIn value
            elapsedTimeInSeconds >= expiresIn
        }
    }

    fun getAccessTokenWithType() = "$tokenType $accessToken"
}