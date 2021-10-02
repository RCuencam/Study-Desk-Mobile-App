package com.example.studydesk

import com.google.gson.annotations.SerializedName

class ResponseUserAuth (
    @SerializedName("id")
    var id:Int,
    @SerializedName("email")
    var email:String,
    @SerializedName("token")
    var token:String,
    )