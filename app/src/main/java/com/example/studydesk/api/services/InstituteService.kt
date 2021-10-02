package com.example.studydesk.api.services

import com.example.studydesk.api.entities.Institute
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InstituteService {
    @GET("institutes")
    fun getAllInstitutes(): Call<List<Institute>>

    @GET("institutes/{id}")
    fun getInstituteById(@Path("id") id: Long): Call<Institute>
}