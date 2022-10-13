package com.zoo.dongyeah

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object RetrofitClient {
    private var instance: Retrofit? = null

    fun getInstance(): Retrofit {
        instance = Retrofit.Builder()
            .baseUrl(BuildConfig.DATA_URL)
            .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
            .build()
        return instance!!
    }
}