package com.zoo.dongyeah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.zoo.dongyeah.databinding.ActivityHosInfoBinding
import com.zoo.dongyeah.databinding.ActivityMainBinding
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActivityHosInfo : AppCompatActivity() {
    private val LOG_TAG = "com.zoo.dongyeah"
    private lateinit var binding: ActivityHosInfoBinding

    private val mapRadius = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHosInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)
        getHospitalData(latitude, longitude)

    }

    fun onClickBack(view: View) {}
    fun onClickBook(view: View) {}

    private fun getHospitalData(latitude: Double, longitude: Double) {
        val service = RetrofitClient.getInstance().create(HospitalAPI::class.java)
        val call = service.getHospitalData(latitude, longitude, mapRadius)
        call.enqueue(object : Callback<HospitalData> {
            override fun onResponse(call: Call<HospitalData>, response: Response<HospitalData>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        Log.e(LOG_TAG, response.body().toString())

                        val result  = body.body.items.item
                        binding.txtHosName.text = result.get(0).hosName
                        binding.txtHosCode.text = result.get(0).hosCode
                        binding.txtHosAddr.text = result.get(0).address
                        binding.txtHosTel.text = result.get(0).tel
                        binding.txtHosUrl.text = result.get(0).hosUrl

                    } else {
                        Log.e(LOG_TAG, "response error")
                    }
                }
            }

            override fun onFailure(call: Call<HospitalData>, t: Throwable) {
                Log.e(LOG_TAG, t.printStackTrace().toString())
                Log.e(LOG_TAG, t.message)

                Toast.makeText(applicationContext, "검색 결과가 없습니다.", Toast.LENGTH_LONG)
            }

        })
    }
}