package com.example.coviddefender.BtnNavFragment

import android.content.Context
import android.preference.PreferenceManager

class PrefUtil {
    companion object {
        fun getTimerLength(context: Context): Int {
            return 15
        }

        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID =
            "com.example.coviddefender.BtnNavFragment.Timer.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, 0)
        }

        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID, seconds)
            editor.apply()
        }

        private const val TIMER_STATE_ID =
            "com.example.coviddefender.BtnNavFragment.Timer.timer_state"

        fun getTimerState(context: Context): Timer.TimerState {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            var ordinal = preferences.getInt(TIMER_STATE_ID, 0)
            return Timer.TimerState.values()[ordinal]
        }

        fun setTimerState(state: Timer.TimerState, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            var ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()
        }

        private const val SECONDS_REMAINING_ID =
            "com.example.coviddefender.BtnNavFragment.Timer.seconds_remaining"

        fun getSecondsRemaining(context: Context): Long {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID, 0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context) {
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID, seconds)
            editor.apply()
        }
    }
}