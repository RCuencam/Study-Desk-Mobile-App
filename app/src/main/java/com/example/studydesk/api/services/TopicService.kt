package com.example.studydesk.api.services

import com.example.studydesk.api.entities.Topic
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TopicService {
    @GET("courses/{id}/topics")
    fun getTopicsByCourseId(@Path("id") id: Long): Call<List<Topic>>
}