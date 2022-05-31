package com.example.coviddefender.recyclerview
import androidx.annotation.DrawableRes

data class ToDo (
    @DrawableRes var thumbnail: Int,
    var description: String
)