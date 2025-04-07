package com.fauzangifari.surata.ui.screens.detail

import com.fauzangifari.surata.domain.model.Letter

data class DetailState(
    val isLoading: Boolean = false,
    val data: Letter? = null,
    val error: String = ""
)