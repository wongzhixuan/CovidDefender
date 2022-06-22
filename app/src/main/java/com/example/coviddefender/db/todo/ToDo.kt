package com.example.coviddefender.db.todo
import androidx.annotation.DrawableRes

data class ToDo (
    @DrawableRes var thumbnail: Int,
    var description: String
)