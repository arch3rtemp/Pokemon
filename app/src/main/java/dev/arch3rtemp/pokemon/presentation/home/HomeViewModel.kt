package dev.arch3rtemp.pokemon.presentation.home

import androidx.annotation.ColorRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.domain.useCase.GetPokesUseCase
import dev.arch3rtemp.pokemon.domain.useCase.UpdatePokeUseCase
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
class HomeViewModel @Inject constructor(
    private val getPokesUseCase: GetPokesUseCase
) : ViewModel() {

    private var coroutineExceptionHandler: CoroutineExceptionHandler
    private var job: Job = Job()

    private val _state = MutableLiveData<HomeContract.State>().apply {
        value = HomeContract.State(HomeContract.HomeState.Idle)
    }
    val state: LiveData<HomeContract.State>
        get() = _state

    private val _effect = Channel<HomeContract.Effect>()
    val effect: Channel<HomeContract.Effect>
        get() = _effect

    init {
        coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Error(message = exception.message!!))
        }

        getPokemons()
    }

    private fun getPokemons() {
        viewModelScope.launch(coroutineExceptionHandler) {
            _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Loading)

            val pokemons = withContext(Dispatchers.IO) {
                getPokesUseCase.invoke()
            }

            when(pokemons) {
                is Resource.Error -> {
                    _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Error(message = pokemons.message!!))
                    _effect.send(HomeContract.Effect.ShowSnackBar((pokemons.message!!)))
                }
                is Resource.Exception -> {
                    _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Error(message = pokemons.e.message!!))
                    _effect.send(HomeContract.Effect.ShowSnackBar((pokemons.e.message!!)))
                }
                is Resource.Success -> {
                    if (pokemons.data.isEmpty()) {
                        _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Empty)
                    } else {
                        _state.value = _state.value?.copy(homeState = HomeContract.HomeState.Success(pokemons = pokemons.data))
                    }
                }
            }
        }
    }
}