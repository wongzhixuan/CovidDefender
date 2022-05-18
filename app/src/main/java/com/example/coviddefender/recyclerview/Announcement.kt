package com.example.coviddefender.recyclerview


import androidx.annotation.DrawableRes

data class Announcement (
        @DrawableRes var thumbnail: Int,
        var description: String
        )