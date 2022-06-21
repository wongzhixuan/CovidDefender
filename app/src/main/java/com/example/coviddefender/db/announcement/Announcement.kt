package com.example.coviddefender.db.announcement


import androidx.annotation.DrawableRes

data class Announcement (
        @DrawableRes var thumbnail: Int,
        var description: String,
        var link: String
        )