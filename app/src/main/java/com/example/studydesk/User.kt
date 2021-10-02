package com.example.studydesk

import com.google.gson.annotations.SerializedName

class User (
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String)