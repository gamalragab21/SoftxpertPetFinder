package net.soft.core.common.data.repository.remote.converter

import com.google.gson.JsonParser
import com.google.gson.stream.JsonReader
import net.soft.android.helpers.gson.converter.INetworkFailureConverter
import net.soft.android.helpers.gson.parsing.ResponseBodyParsingStrategy
import net.soft.core.common.data.consts.Constants
import net.soft.core.common.data.model.exception.LeonException
import net.soft.core.R
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException
import java.io.StringReader
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal class ResponseBodyConverter : INetworkFailureConverter {

    override fun produceErrorBodyFailure(code: Int, errorBody: ResponseBody?): Throwable {
        return when (code) {
            401 -> LeonException.Client.Unauthorized

            in 400..499 -> buildClientException(code, errorBody)

            in 500..599 -> LeonException.Server.InternalServerError(
                httpErrorCode = code, errorBody?.string()
            )

            else -> LeonException.Client.Unhandled(httpErrorCode = code, errorBody?.string())
        }
    }

    override fun produceFailure(throwable: Throwable): Throwable {
        return when (throwable) {
            is SocketTimeoutException, is UnknownHostException, is IOException -> LeonException.Network.Retrial(
                messageRes = R.string.error_io_unexpected_message,
                message = "Retrial network error."
            )

            else -> LeonException.Network.Unhandled(
                messageRes = R.string.error_unexpected_message,
                message = "NetworkException Unhandled error."
            )
        }
    }

    private fun buildClientException(code: Int, errorBody: ResponseBody?): LeonException {
        return if (errorBody == null) LeonException.Client.Unhandled(
            httpErrorCode = code, "There is no error body for this code."
        ) else try {
            prepareErrorBodyInternalCode(code, errorBody)
        } catch (e: Exception) {
            LeonException.Client.Unhandled(httpErrorCode = code, e.message)
        }
    }

    private fun prepareErrorBodyInternalCode(code: Int, errorBody: ResponseBody): LeonException {
        // Create a JsonElement from the JSON string
        val jsonObject = JSONObject(errorBody.string())

        if (jsonObject.isNull(Constants.CODE))
            jsonObject.put(Constants.CODE, code)

        val jsonElement = JsonParser.parseString(jsonObject.toString())

        // Create a JsonReader from the JsonElement
        val jsonReader = JsonReader(StringReader(jsonElement.toString()))

        val result = ResponseBodyParsingStrategy()
        return result.parse(jsonReader)
    }
}