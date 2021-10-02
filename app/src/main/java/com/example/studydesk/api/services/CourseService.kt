package com.example.studydesk.api.services

import com.example.studydesk.api.entities.Course
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CourseService {
    @GET("career/{id}/courses")
    fun getCoursesByCareerId(@Path("id") id: Long): Call<List<Course>>
}