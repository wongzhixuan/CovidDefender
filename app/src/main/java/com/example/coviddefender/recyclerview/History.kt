package com.example.coviddefender.recyclerview

import androidx.annotation.DrawableRes

data class History (
    @DrawableRes var thumbnail: Int,
    var location_name: String,
    var checkin_time: String,
    var check_out_state: Boolean)