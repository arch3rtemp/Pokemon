package dev.arch3rtemp.pokemon.presentation.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.pokemon.domain.useCase.LoadPokeUseCase
import dev.arch3rtemp.pokemon.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val loadPokeUseCase: LoadPokeUseCase
) : ViewModel() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var job: Job = Job()

    private val _state = MutableLiveData<DetailsContract.State>().apply {
        value = DetailsContract.State(DetailsContract.DetailsState.Idle)
    }

    val state: LiveData<DetailsContract.State>
        get() = _state

    private val _effect = Channel<DetailsContract.Effect>()

    val effect: Channel<DetailsContract.Effect>
        get() = _effect

    private val _event = MutableSharedFlow<DetailsContract.Event>()
    val event: SharedFlow<DetailsContract.Event>
        get() = _event

    fun setEvent(event: DetailsContract.Event) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvents(it)
            }
        }
    }

    private fun handleEvents(event: DetailsContract.Event) {
        when(event) {
            is DetailsContract.Event.OnPokeLoad -> {
                getPokemon(event.id)
            }
        }
    }

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _state.value = _state.value?.copy(detailsState = DetailsContract.DetailsState.Error(exception.message!!))
        }

        subscribeEvents()
    }

    private fun getPokemon(id: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            _state.value = _state.value?.copy(detailsState = DetailsContract.DetailsState.Loading)

            val pokemon = withContext(Dispatchers.IO) {
                loadPokeUseCase.invoke(id)
            }

            when(pokemon) {
                is Resource.Error -> {
                    _state.value = _state.value?.copy(detailsState = DetailsContract.DetailsState.Error(message = pokemon.message!!))
                }
                is Resource.Exception -> {
                    _state.value = _state.value?.copy(detailsState = DetailsContract.DetailsState.Error(message = pokemon.e.message!!))
                }
                is Resource.Success -> {
                    _state.value = _state.value?.copy(detailsState = DetailsContract.DetailsState.Success(pokemon = pokemon.data))
                }
            }
        }
    }
}