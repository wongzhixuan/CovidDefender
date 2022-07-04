package com.example.coviddefender.entity
import androidx.annotation.DrawableRes

data class ToDo (
    @DrawableRes var thumbnail: Int,
    var description: String
)