package com.fauzangifari.surata.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fauzangifari.surata.common.Resource
import com.fauzangifari.surata.domain.usecase.GetLetterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getLetterUseCase: GetLetterUseCase
) : ViewModel() {

    private var isLoaded = false

    private val _state = MutableStateFlow(LetterState())
    val state: StateFlow<LetterState> = _state

    init {
        getLetters()
    }

    private fun getLetters() {
        if (isLoaded) return

        viewModelScope.launch {
            getLetterUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        isLoaded = true
                        _state.update {
                            it.copy(
                                isLoading = false,
                                data = result.data ?: emptyList(),
                                error = ""
                            )
                        }
                    }
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = result.message ?: "Terjadi kesalahan"
                            )
                        }
                    }
                }
            }
        }
    }
}

