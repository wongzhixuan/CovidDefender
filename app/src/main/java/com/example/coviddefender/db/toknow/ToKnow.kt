package com.example.coviddefender.db.toknow
import androidx.annotation.DrawableRes

data class ToKnow (
    @DrawableRes var thumbnail: Int,
    var description: String
)