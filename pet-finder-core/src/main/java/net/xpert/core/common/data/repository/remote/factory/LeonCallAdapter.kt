package net.xpert.core.common.data.repository.remote.factory

import net.xpert.android.helpers.gson.converter.INetworkFailureConverter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class LeonCallAdapter(private val errorConverter: INetworkFailureConverter) :
    CallAdapter<ResponseBody, Call<ResponseBody>> {

    override fun responseType(): Type = ResponseBody::class.java

    override fun adapt(call: Call<ResponseBody>): Call<ResponseBody> {
        return LeonCall(call, errorConverter)
    }
}