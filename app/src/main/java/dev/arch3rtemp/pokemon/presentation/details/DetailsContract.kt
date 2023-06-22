package dev.arch3rtemp.pokemon.presentation.details

import com.arch3rtemp.android.moviesapp.util.UiEffect
import com.arch3rtemp.android.moviesapp.util.UiEvent
import com.arch3rtemp.android.moviesapp.util.UiState
import dev.arch3rtemp.pokemon.domain.model.Pokemon
import dev.arch3rtemp.pokemon.presentation.home.HomeContract

class DetailsContract {

    sealed class DetailsState {

        object Idle : DetailsState()
        object Loading : DetailsState()
        data class Error(val message: String) : DetailsState()
        data class Success(val pokemon: Pokemon) : DetailsState()
    }

    sealed class Event : UiEvent {
        data class OnPokeLoad(val id: Int) : Event()
    }

    sealed class Effect : UiEffect {
        data class ShowSnackBar(val message: String) : Effect()
    }

    data class State(val detailsState: DetailsState) : UiState
}