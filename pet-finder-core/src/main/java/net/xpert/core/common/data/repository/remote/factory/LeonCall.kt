package net.xpert.core.common.data.repository.remote.factory

import net.xpert.android.helpers.gson.converter.INetworkFailureConverter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

internal class LeonCall(
    private val delegate: Call<ResponseBody>, private val errorConverter: INetworkFailureConverter
) : Call<ResponseBody> by delegate {

    override fun execute(): retrofit2.Response<ResponseBody> {
        throw UnsupportedOperationException("LeonCall doesn't support execute()")
    }

    override fun clone(): Call<ResponseBody> {
        return LeonCall(delegate.clone(), errorConverter)
    }

    override fun enqueue(callback: Callback<ResponseBody>) {
        delegate.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>, response: retrofit2.Response<ResponseBody>
            ) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    val failure = errorConverter.produceErrorBodyFailure(
                        response.code(), response.errorBody()
                    )
                    callback.onFailure(call, failure)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.onFailure(call, errorConverter.produceFailure(t))
            }
        })
    }
}