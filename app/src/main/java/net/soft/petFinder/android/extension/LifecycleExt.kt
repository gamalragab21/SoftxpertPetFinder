package net.soft.petFinder.android.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun <T : Any?, L : SharedFlow<T>> LifecycleOwner.observe(sharedFlow: L, body: (T) -> Unit) {
    repeatOnStarted {
        var job: Job? = null
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        job = lifecycleScope.launch {
                            sharedFlow.collect {
                                body.invoke(it)
                            }
                        }
                    }

                    Lifecycle.Event.ON_STOP -> {
                        job?.cancel()
                    }

                    else -> {}
                }
            }
        })
    }
}

inline fun <T> LifecycleOwner.repeatOnStarted(
    crossinline block: suspend CoroutineScope.() -> T
) {
    var executed = false // flag to track if the block has been executed
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            if (!executed) {
                block()
                executed = true
            }
        }
    }
}
