package net.soft.core.common.domain.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import net.soft.core.common.data.model.Resource

abstract class UseCaseLocalThenRemoteThenCache<Domain, in Body> :
    UseCaseRemoteThenCache<Domain, Body>() {

    protected abstract fun executeLocalDS(body: Body?): Flow<Domain>

    protected abstract fun performRemoteOperation(domain: Domain?): Boolean

    override operator fun invoke(
        scope: CoroutineScope, body: Body?, multipleInvoke: Boolean,
        onResult: (Resource<Domain>) -> Unit
    ) {
        scope.launch(Dispatchers.Main) {
            if (multipleInvoke.not())
                onResult.invoke(Resource.loading())

            // Run local first
            runFlow(executeLocalDS(body), body, onResult).collect { localData ->
                if (performRemoteOperation(localData)) { // call network and get result
                    runFlow(executeRemoteDS(body), body, onResult).collect {
                        if (performLocalOperation(it))
                            executeLocalOperation(it, body)

                        onResult.invoke(invokeSuccessState(it, body))

                        if (multipleInvoke.not())
                            onResult.invoke(Resource.loading(false))
                    }
                } else { // get local
                    onResult.invoke(invokeSuccessState(localData, body))

                    if (multipleInvoke.not())
                        onResult.invoke(Resource.loading(false))
                }
            }
        }
    }

    override operator fun invoke(body: Body?, multipleInvoke: Boolean): Flow<Resource<Domain>> =
        channelFlow {
            if (multipleInvoke.not())
                send(Resource.loading())

            // Run local first
            runFlow(executeLocalDS(body), body) {
                send(it)
            }.collect { localData ->
                if (performRemoteOperation(localData)) { //call network and get result
                    runFlow(executeRemoteDS(body), body) {
                        send(it)
                    }.collect {
                        if (performLocalOperation(it))
                            executeLocalOperation(it, body)

                        send(invokeSuccessState(it, body))

                        if (multipleInvoke.not())
                            send(Resource.loading(false))
                    }
                } else { // get local
                    send(invokeSuccessState(localData, body))

                    if (multipleInvoke.not())
                        send(Resource.loading(false))
                }
            }
        }
}