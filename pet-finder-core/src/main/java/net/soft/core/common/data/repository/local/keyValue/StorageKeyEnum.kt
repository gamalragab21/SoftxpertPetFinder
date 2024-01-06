package net.soft.core.common.data.repository.local.keyValue

import net.soft.core.common.domain.repository.local.keyValue.IStorageKeyEnum

internal enum class StorageKeyEnum(override val keyValue: String) : IStorageKeyEnum {
    USER_TOKEN("user_token-Key");
}