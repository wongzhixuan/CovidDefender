package com.example.coviddefender.BtnNavFragment

import android.opengl.Visibility
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.coviddefender.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class Timer : Fragment() {

    enum class TimerState{
        Stopped, Paused, Running
    }

    private lateinit var timer : CountDownTimer
    private var timerLengthSeconds: Long = 0L
    private var timerState = TimerState.Stopped
    private var secondsRemaining = 0L
    lateinit var timer_progressbar : MaterialProgressBar
    lateinit var tv_time : TextView
    lateinit var fab_play : FloatingActionButton
    lateinit var fab_stop : FloatingActionButton
    lateinit var btn_next : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_timer, container, false)
        timer_progressbar = view.findViewById(R.id.timer_progressbar)
        tv_time = view.findViewById(R.id.tv_time)
        fab_play = view.findViewById(R.id.fab_play)
        fab_stop = view.findViewById(R.id.fab_stop)
        btn_next = view.findViewById(R.id.btn_next)

        fab_play.setOnClickListener{
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        fab_stop.setOnClickListener {
            timer.cancel()
            onTimerFinished()
            btn_next.isEnabled = true
        }

        btn_next?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_timer_to_test_result)
        })

        // back button
        val self_test_back = view.findViewById<ImageButton>(R.id.self_test_back)
        self_test_back?.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_timer_to_instruction)
        })

        return view
    }

    override fun onResume() {
        super.onResume()
        timerState = TimerState.Paused
        initTimer()
    }

    private fun initTimer(){
        timerState = PrefUtil.getTimerState(requireContext())
        if (timerState==TimerState.Stopped){
            setNewTimerLength()
        }
        else{
            setPreviousTimerLength()
        }
        secondsRemaining = if (timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(requireContext())
        else{
            timerLengthSeconds
        }

        if (timerState==TimerState.Running){
            startTimer()
        }
        updateButtons()
        updateCountdownUI()
    }

    private fun onTimerFinished(){
        timerState = TimerState.Stopped

        setNewTimerLength()

        timer_progressbar.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds,requireContext())
        secondsRemaining = timerLengthSeconds
        updateButtons()
        updateCountdownUI()
    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining*1000, 1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished:Long){
                secondsRemaining = millisUntilFinished / 1000
                updateCountdownUI()
            }
        }.start()
    }

    private fun setNewTimerLength(){
        val lengthinMinutes = PrefUtil.getTimerLength(requireContext())
        timerLengthSeconds = (lengthinMinutes*60L)
        timer_progressbar.max = timerLengthSeconds.toInt()
    }

    private fun setPreviousTimerLength(){
        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(requireContext())
        timer_progressbar.max = timerLengthSeconds.toInt()
    }

    private fun updateCountdownUI(){
        val minutesUntilFinished = secondsRemaining/60
        val secondsInMinutesUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinutesUntilFinished.toString()
        tv_time.text = "$minutesUntilFinished:${
            if(secondsStr.length == 2) secondsStr
            else "0" + secondsStr}"
        timer_progressbar.progress = (timerLengthSeconds - secondsRemaining).toInt()
    }

    private fun updateButtons(){
        when(timerState){
            TimerState.Running->{
                fab_play.isEnabled = false
                fab_stop.isEnabled = true
            }
            TimerState.Stopped->{
                fab_play.isEnabled = true
                fab_stop.isEnabled = false
            }
            TimerState.Paused->{
                fab_play.isEnabled = true
                fab_stop.isEnabled = true
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = Timer()
    }
}