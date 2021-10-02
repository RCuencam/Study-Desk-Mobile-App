package com.example.studydesk.api.services

import com.example.studydesk.api.entities.Career
import com.example.studydesk.api.entities.Institute
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CareerService {
    @GET("institutes/{id}/careers")
    fun getCareersByInstituteId(@Path("id") id: Long): Call<List<Career>>
}