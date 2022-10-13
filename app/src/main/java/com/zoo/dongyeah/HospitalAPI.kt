package com.zoo.dongyeah

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface HospitalAPI {
    @GET("getHospBasisList1")
    fun getHospitalData(
        @Query("yPos") yPos: Double,
        @Query("xPos") xPos: Double,
        @Query("radius") radius: Int,
        @Query("serviceKey", encoded = true) ServiceKey: String = BuildConfig.DATA_API_KEY
    ): Call<HospitalData>


    @GET("getHospBasisList1")
    fun getSearchData(
        @Query("emdongNm") searchData: String?,
        @Query("serviceKey", encoded = true) ServiceKey: String = BuildConfig.DATA_API_KEY
    ): Call<HospitalData>
}