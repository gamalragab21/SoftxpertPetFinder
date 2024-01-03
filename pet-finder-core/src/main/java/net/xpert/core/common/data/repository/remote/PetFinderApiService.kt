package net.xpert.core.common.data.repository.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

internal interface PetFinderApiService {

    @DELETE("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun delete(
        @Path(value = "pathUrl", encoded = true) pathUrl: String, @Body body: Any,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @POST("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun post(
        @Path(value = "pathUrl", encoded = true) pathUrl: String, @Body body: Any,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @PUT("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun put(
        @Path(value = "pathUrl", encoded = true) pathUrl: String, @Body body: Any,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): ResponseBody


    @POST("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun postWithHeaderResponse(
        @Path(value = "pathUrl", encoded = true) pathUrl: String, @Body body: Any,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>

    @GET("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun get(
        @Path(value = "pathUrl", encoded = true) pathUrl: String,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): ResponseBody

    @GET("{pathUrl}")
    @JvmSuppressWildcards
    suspend fun getWithHeaderResponse(
        @Path(value = "pathUrl", encoded = true) pathUrl: String,
        @HeaderMap headerMap: Map<String, Any>, @QueryMap queryParams: Map<String, Any>
    ): Response<ResponseBody>
}