package com.example.studydesk

import com.google.gson.annotations.SerializedName

class Student (
    @SerializedName("name")
    var name: String,
    @SerializedName("lastName")
    var lastName:String,
    @SerializedName("logo")
    var logo:String,
    @SerializedName("email")
    var email:String,
    @SerializedName("password")
    var password:String,
    )