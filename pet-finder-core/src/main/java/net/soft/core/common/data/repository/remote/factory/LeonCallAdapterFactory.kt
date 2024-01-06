package net.soft.core.common.data.repository.remote.factory

import net.soft.android.helpers.gson.converter.INetworkFailureConverter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class LeonCallAdapterFactory private constructor(private val errorConverter: INetworkFailureConverter) :
    CallAdapter.Factory() {

    companion object {
        fun create(errorConverter: INetworkFailureConverter): CallAdapter.Factory {
            return LeonCallAdapterFactory(errorConverter)
        }
    }

    override fun get(
        returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java) {
            return null
        }
        val responseType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawType = getRawType(responseType)

        if (rawType != ResponseBody::class.java) {
            return null
        }

        return LeonCallAdapter(errorConverter)
    }
}