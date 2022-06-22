package com.example.coviddefender.db.vaccine_info

import androidx.annotation.DrawableRes

data class Vaccine_Info (
    @DrawableRes var thumbnail: Int,
    var description: String
        )