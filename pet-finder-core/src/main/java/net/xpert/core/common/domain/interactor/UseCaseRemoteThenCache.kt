package net.xpert.core.common.domain.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import net.xpert.core.common.data.model.Resource

abstract class UseCaseRemoteThenCache<Domain, in Body> :
    UseCaseRemote<Domain, Body>() {

    override operator fun invoke(
        scope: CoroutineScope, body: Body?, multipleInvoke: Boolean,
        onResult: (Resource<Domain>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            if (multipleInvoke.not())
                onResult.invoke(Resource.loading())

            runFlow(executeRemoteDS(body), body, onResult).collect {
                if (performLocalOperation(it))
                    executeLocalOperation(it, body)

                onResult.invoke(invokeSuccessState(it, body))

                if (multipleInvoke.not())
                    onResult.invoke(Resource.loading(false))
            }
        }
    }

    override operator fun invoke(body: Body?, multipleInvoke: Boolean): Flow<Resource<Domain>> =
        channelFlow {
            if (multipleInvoke.not())
                send(Resource.loading())

            runFlow(executeRemoteDS(body), body) {
                send(it)
            }.collect {
                if (performLocalOperation(it))
                    executeLocalOperation(it, body)

                send(invokeSuccessState(it, body))

                if (multipleInvoke.not())
                    send(Resource.loading(false))
            }
        }

    protected abstract fun performLocalOperation(domain: Domain): Boolean

    protected abstract suspend fun executeLocalOperation(domain: Domain, body: Body?)
}