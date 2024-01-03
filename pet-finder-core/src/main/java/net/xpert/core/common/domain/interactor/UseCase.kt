package net.xpert.core.common.domain.interactor

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import net.xpert.core.common.data.model.Resource
import net.xpert.core.common.data.model.exception.LeonException

/**
- Acts as a contract for all the use cases in our application:
 * Abstract class for a Use Case (Interact in terms of Clean Architecture).
 * This abstraction represents an execution unit for different use cases (this means that any use
 * case in the application should implement this contract).
 *
 * By convention each [UseCase] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.

 * @param Domain: the result returned after mapping the response to to the View
 * @param Body: a parameters class which will be consumed inside the [invoke] function
 *                in case we need extra data for our use case.
 */
abstract class UseCase<Domain, in Body> {

    abstract operator fun invoke(
        scope: CoroutineScope, body: Body? = null, multipleInvoke: Boolean = false,
        onResult: (Resource<Domain>) -> Unit = {}
    )

    abstract operator fun invoke(
        body: Body? = null, multipleInvoke: Boolean = false
    ): Flow<Resource<Domain>>

    protected open fun invokeSuccessState(domain: Domain, body: Body?): Resource<Domain> {
        return validateResponseModel(domain, body) ?: Resource.success(domain)
    }

    protected open suspend fun invokeFailureState(
        exception: LeonException, body: Body?
    ): Resource<Domain> {
        return validateFailureResponse(exception, body) ?: Resource.failure(exception)
    }

    protected abstract fun validateResponseModel(domain: Domain, body: Body?): Resource<Domain>?

    protected abstract suspend fun validateFailureResponse(
        exception: LeonException, body: Body?
    ): Resource<Domain>?

    protected abstract suspend fun <M> runFlow(
        requestExecution: Flow<M>, body: Body?, onResult: suspend (Resource<Domain>) -> Unit = {}
    ): Flow<M>

    open fun runFlowInsideIODisputer(): Boolean = true
}