package net.soft.core.common.domain.repository.remote

import java.lang.reflect.Type

internal interface INetworkProvider {

    suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): ResponseBody

    suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): ResponseBody

    suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): ResponseBody

    suspend fun <ResponseBody, RequestBody> postWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null, requestBody: RequestBody? = null
    ): Pair<ResponseBody, Map<String, String>>

    suspend fun <ResponseBody> get(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null
    ): ResponseBody

    suspend fun <ResponseBody> getWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>? = null,
        queryParams: Map<String, Any>? = null
    ): Pair<ResponseBody, Map<String, String>>
}