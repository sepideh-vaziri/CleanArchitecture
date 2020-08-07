package com.architecture.domain.interactor

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 */

abstract class UseCase<T, Params> {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    /**
     * Executing the current [UseCase] on the background thread.
     */
    internal abstract suspend fun executeOnBackground(params: Params?) : T

    /**
     * Executes the current use case.
     *
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    fun execute(params: Params?, onComplete: ((useCaseResult: UseCaseResult<T>) -> Unit)) {
        if (parentJob.isCancelled) {
            parentJob = Job()
        }

        CoroutineScope(foregroundContext + parentJob).launch {

            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(params)
                }

                onComplete.invoke(UseCaseResult.Success(result))
            }
            catch (t: Throwable) {
                onComplete.invoke(UseCaseResult.Error(t))
            }

        }

    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    open fun dispose() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

}
