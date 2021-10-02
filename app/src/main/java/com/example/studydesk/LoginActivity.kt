package com.example.studydesk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isInvisible
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.content.Intent




class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        val btLogin = findViewById<Button>(R.id.btLogin)

        val etUser = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val tvErrorLogin = findViewById<TextView>(R.id.tvErrorLogin)
        val sharedPreferences = SharedPreferences(this)

        btLogin.setOnClickListener {


            val name = etUser.text.toString()
            val password = etPassword.text.toString()


            val myToken:String = authenticateUser(name,password,sharedPreferences)

            tvErrorLogin.isInvisible = myToken != ""


            if (sharedPreferences.getValue("token") != null)
            {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show()
            }
        }

        /*btRetrieve.setOnClickListener {
            tvRetrieve.setText(sharedPreferences.getValue("name"))
        }*/
    }


    private fun authenticateUser(name:String,password:String,sharedPreferences:SharedPreferences):String {

        var myToken:String = ""
        //Creo una instancia de retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://studydeskapi.azurewebsites.net")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creo una instancia de la interfaz
        val userService: UserService = retrofit.create(UserService::class.java)

        val user: User = User(name,password)
        //creo una variable y le asigno el servicio
        val request = userService.authenticateUser(user,"json")

        request.enqueue(object : Callback<ResponseUserAuth> {
            override fun onResponse(call: Call<ResponseUserAuth>, response: Response<ResponseUserAuth>) {
                if (response.isSuccessful) {
                    val auth = response.body()
                    if (auth != null) {
                        myToken = auth.token
                        sharedPreferences.save("token", myToken)
                    }
                    Log.d("response", Gson().toJson(auth))
                }

            }

            override fun onFailure(call: Call<ResponseUserAuth>, t: Throwable) {
                Log.d("responseFail", t.toString())
            }
        })


        return myToken


    }
}