package com.example.coviddefender.recyclerview

import androidx.annotation.DrawableRes

data class Vaccine_Info (
    @DrawableRes var thumbnail: Int,
    var description: String
        )