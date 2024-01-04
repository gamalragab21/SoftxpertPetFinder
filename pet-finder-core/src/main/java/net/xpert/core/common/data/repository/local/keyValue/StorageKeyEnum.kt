package net.xpert.core.common.data.repository.local.keyValue

import net.xpert.core.common.domain.repository.local.keyValue.IStorageKeyEnum

internal enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    USER_TOKEN("user_token-Key"),
    IS_FIRST_TIME("is_first_time_usage_key"),
    UNDEFINED("");
}