package net.soft.core.common.data.repository.remote

import net.soft.android.extentions.getModelFromJSON
import net.soft.core.common.domain.repository.remote.INetworkProvider
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import java.lang.reflect.Type

internal class RetrofitNetworkProvider(private val apiService: PetFinderApiService) :
    INetworkProvider {

    override suspend fun <ResponseBody, RequestBody> delete(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiService.delete(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), body = requestBody ?: Unit
        )
        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> post(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiService.post(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), body = requestBody ?: Unit
        )

        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> put(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): ResponseBody {
        val response = apiService.put(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), body = requestBody ?: Unit
        )

        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody, RequestBody> postWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?, requestBody: RequestBody?
    ): Pair<ResponseBody, Map<String, String>> {
        val response = apiService.postWithHeaderResponse(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf(), body = requestBody ?: Unit
        )
        return when {
            response.isSuccessful -> Pair(
                (response.body() ?: "".toResponseBody()).string()
                    .getModelFromJSON(responseWrappedModel), response.headers().toMap()
            )

            else -> throw HttpException(response)
        }
    }

    override suspend fun <ResponseBody> get(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): ResponseBody {
        val response = apiService.get(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return response.string().getModelFromJSON(responseWrappedModel)
    }

    override suspend fun <ResponseBody> getWithHeaderResponse(
        responseWrappedModel: Type, pathUrl: String, headers: Map<String, Any>?,
        queryParams: Map<String, Any>?
    ): Pair<ResponseBody, Map<String, String>> {
        val response = apiService.getWithHeaderResponse(
            pathUrl = pathUrl, headerMap = headers ?: hashMapOf(),
            queryParams = queryParams ?: hashMapOf()
        )
        return when {
            response.isSuccessful -> Pair(
                (response.body() ?: "".toResponseBody()).string()
                    .getModelFromJSON(responseWrappedModel), response.headers().toMap()
            )

            else -> throw HttpException(response)
        }
    }
}