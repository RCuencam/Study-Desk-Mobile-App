package com.example.studydesk

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/api/users/authenticate")
    fun authenticateUser(@Body user: User?, @Query("format")format: String): Call<ResponseUserAuth>
}