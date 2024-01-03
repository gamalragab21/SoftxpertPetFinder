package net.xpert.petfinder.android.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

internal abstract class BaseViewModel<S : ViewState> : ViewModel() {

    private val _viewState = MutableSharedFlow<S>()
    val viewState: SharedFlow<S> = _viewState

    //use this method to produce an action to the view
    fun produce(s: S) {
        viewModelScope.launch(Dispatchers.Main) {
            _viewState.emit(s)
        }
    }

    // to switch thread in test
    open fun getIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}