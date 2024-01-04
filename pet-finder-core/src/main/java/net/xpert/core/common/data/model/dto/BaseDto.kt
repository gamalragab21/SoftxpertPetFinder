package net.xpert.core.common.data.model.dto

import com.google.gson.annotations.SerializedName

internal open class BaseDto(
    @SerializedName("detail") var message: String? = null,
    @SerializedName("status") var code: Int? = null
)