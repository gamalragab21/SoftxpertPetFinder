package net.xpert.core.common.data.model.dto

import com.google.gson.annotations.SerializedName

internal open class BaseDto(
    @SerializedName("message") var message: String? = null,
    @SerializedName("code") var code: Int? = null
)