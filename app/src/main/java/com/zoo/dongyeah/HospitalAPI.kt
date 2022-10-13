package com.zoo.dongyeah

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalAPI {
    @GET("getHospBasisList1")
    fun getData(
        @Query("serviceKey")ServiceKey: String = BuildConfig.DATA_API_KEY
    ): Call<HospitalData>
}