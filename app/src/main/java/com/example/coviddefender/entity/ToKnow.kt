package com.example.coviddefender.entity

import androidx.annotation.DrawableRes

data class ToKnow(
    @DrawableRes var thumbnail: Int,
    var description: String
)