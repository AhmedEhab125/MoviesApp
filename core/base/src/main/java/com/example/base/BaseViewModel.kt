package com.example.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base ViewModel class that implements the MVVM pattern with MVI (Model-View-Intent) elements.
 * This abstract class provides a foundation for managing UI state, events, and side effects in a structured way.
 *
 * @param UiState The type representing the UI state, must implement [ViewState]
 * @param Event The type representing UI events, must implement [ViewEvent]
 * @param Effect The type representing side effects, must implement [ViewSideEffect]
 *
 * Features:
 * - State management using [StateFlow]
 * - Event handling using [MutableSharedFlow]
 * - Side effects using [Channel]
 * - Coroutine support with viewModelScope
 */
abstract class BaseViewModel<UiState : ViewState, Event : ViewEvent, Effect : ViewSideEffect> :
    ViewModel() {

    /**
     * Abstract method to set the initial state of the ViewModel.
     * This should be implemented by child classes to define their initial UI state.
     *
     * @return The initial [UiState]
     */
    protected abstract fun setInitialState(): UiState

    /**
     * Abstract method to handle UI events.
     * This should be implemented by child classes to process incoming events and update state accordingly.
     *
     * @param event The [Event] to be handled
     */
    protected abstract fun handleEvents(event: Event)

    private val initialState: UiState by lazy { setInitialState() }

    protected val _viewState: MutableStateFlow<UiState> = MutableStateFlow(initialState)
    val viewState: StateFlow<UiState> = _viewState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    /**
     * Sets up event subscription when ViewModel is initialized.
     * Collects events from [_event] flow and processes them using [handleEvents].
     */
    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    /**
     * Public method to emit new events to be processed by the ViewModel.
     *
     * @param event The [Event] to be processed
     */
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    /**
     * Protected method to update the current UI state.
     *
     * @param reducer A lambda that takes the current state and returns a new state
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    /**
     * Protected method to get the current UI state.
     *
     * @return The current [UiState]
     */
    protected fun getState() = _viewState.value

    /**
     * Protected method to emit side effects.
     *
     * @param builder A lambda that creates and returns an [Effect]
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * Extension function to safely launch and collect Flow results with proper error handling.
     *
     * @param onSuccess Callback for successful result
     * @param onError Callback for error handling
     * @param onStart Callback for flow start
     * @param onComplete Callback for flow completion
     * @param skipAfterInitial Flag to skip collection after initial state
     * @return [Job] or null if skipped
     */
    fun <T> Flow<T>.launchAndCollectResult(
        onSuccess: (T) -> Unit = {},
        onError: (Throwable) -> Unit = {},
        onStart: () -> Unit = {},
        onComplete: (Throwable?) -> Unit = {},
        skipAfterInitial: Boolean = true
    ): Job? {
        return if (initialState == _viewState && skipAfterInitial) {
            null
        } else {
            viewModelScope.launch {
                this@launchAndCollectResult.flowOn(
                    Dispatchers.IO
                ).onStart {
                    onStart()
                }.onCompletion { cause ->
                    onComplete(cause)
                }.catch { exception ->
                    onError(exception)
                }.safeCollect { result ->
                    onSuccess(result)
                }
            }
        }
    }
}
