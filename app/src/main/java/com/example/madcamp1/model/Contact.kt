package com.example.madcamp1.model

import androidx.annotation.StringRes

data class Contact (
    @StringRes val NameResourceId: Int,
    @StringRes val NumberResourceId: Int
)