package net.xpert.core.common.domain.repository.local.keyValue

internal interface IStorageKeyValueFile {
    val storageKV: IStorageKeyValue
    suspend fun clearStorageFile()
    suspend fun deleteStorageFile(): Boolean
}