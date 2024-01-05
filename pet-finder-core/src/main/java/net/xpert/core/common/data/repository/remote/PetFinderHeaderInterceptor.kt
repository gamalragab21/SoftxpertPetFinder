package net.xpert.core.common.data.repository.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import net.xpert.core.common.data.consts.Constants
import net.xpert.core.common.data.model.Resource
import net.xpert.features.getUserTokenUC.domain.interactor.GetLocalUserTokenUC
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

internal class PetFinderHeaderInterceptor(private val getLocalUserTokenUC: GetLocalUserTokenUC) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val requestBuilder = original.newBuilder().apply {
            addHeader(ACCEPT, APP_JSON)
            addHeader(CONTENT_TYPE, APP_JSON)
        }

        requestBuilder.apply {
            val userToken = runBlocking { getUserTokenAsMap() }
            if (userToken.isNotEmpty())
                addHeader(Constants.AUTHORIZATION, userToken)
        }

        return chain.proceed(requestBuilder.build())
    }

    private suspend fun getUserTokenAsMap(): String = withContext(Dispatchers.IO) {
        var token = ""
        getLocalUserTokenUC.invoke(multipleInvoke = true).collect {
            token = when (it) {
                is Resource.Failure -> ""
                is Resource.Progress -> return@collect
                is Resource.Success -> it.model.getAccessTokenWithType()
            }
        }
        return@withContext token
    }

    companion object {
        private const val ACCEPT = "Accept"
        private const val CONTENT_TYPE = "Content-Type"
        private const val APP_JSON = "application/json"
    }
}