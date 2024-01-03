package net.xpert.core.common.data.repository.local.keyValue

import net.xpert.core.common.domain.repository.local.keyValue.IStorageKeyEnum

internal enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    USER_TOKEN("alias-pan-Key"),
    UNDEFINED("");
}