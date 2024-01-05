package net.soft.core.test.utils

import app.cash.turbine.ReceiveTurbine
import com.google.common.truth.Truth
import net.soft.core.common.data.model.Resource

internal suspend fun <M> ReceiveTurbine<Resource<M>>.runTestCaseBlock(onResult: suspend (Resource<M>) -> Unit = {}) {
    val loading = awaitItem()
    Truth.assertThat(loading).isEqualTo(Resource.Progress(true, null))
    val result = awaitItem()
    onResult.invoke(result)
    val finish = awaitItem()
    Truth.assertThat(finish).isEqualTo(Resource.Progress(false, null))
    awaitComplete()
}