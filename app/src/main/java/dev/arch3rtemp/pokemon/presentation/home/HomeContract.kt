package dev.arch3rtemp.pokemon.presentation.home

import com.arch3rtemp.android.moviesapp.util.UiEffect
import com.arch3rtemp.android.moviesapp.util.UiEvent
import com.arch3rtemp.android.moviesapp.util.UiState
import dev.arch3rtemp.pokemon.domain.model.Pokemon

class HomeContract {

    sealed class HomeState {
        object Idle : HomeState()
        object Loading : HomeState()
        object Empty : HomeState()
        data class Error(val message: String) : HomeState()
        data class Success(val pokemons: List<Pokemon> = listOf()) : HomeState()
    }

    sealed class Event : UiEvent {
        object OnClickPoke : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect()
    }

    data class State(val homeState: HomeState) : UiState
}