package com.example.coviddefender.BtnNavFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.coviddefender.R
import org.json.JSONException
import org.json.JSONObject

class DailyUpdates : Fragment() {

    lateinit var tv_active_case: TextView
    lateinit var tv_confirm_case: TextView
    lateinit var tv_death: TextView
    lateinit var tv_recover: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view:View = inflater.inflate(R.layout.fragment_daily_updates, container, false)

        // link widgets
        tv_active_case = view.findViewById(R.id.tv_card_1_1)
        tv_death = view.findViewById(R.id.tv_card_3_1)
        tv_recover = view.findViewById(R.id.tv_card_4_1)
        val url: String = "https://disease.sh/v3/covid-19/all"
        val request: StringRequest = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener { response ->
                try{
                    val jsonObj: JSONObject = JSONObject(response.toString())
                    // set text
                    tv_active_case.setText(jsonObj.getString("todayCases"))
                    tv_death.setText(jsonObj.getString("todayDeaths"))
                    tv_recover.setText(jsonObj.getString("todayRecovered"))

                }
                catch (e: JSONException){
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {  })
        val request_queue: RequestQueue = Volley.newRequestQueue(context)
        request_queue.add(request)

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = DailyUpdates()
    }
}