package com.example.studydesk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.studydesk.api.entities.Career
import com.example.studydesk.api.entities.Course
import com.example.studydesk.api.entities.Institute
import com.example.studydesk.api.entities.Topic
import com.example.studydesk.api.services.CareerService
import com.example.studydesk.api.services.CourseService
import com.example.studydesk.api.services.InstituteService
import com.example.studydesk.api.services.TopicService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson
import java.util.Collections.swap

class SearchFileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_file)
        val retrofit = getService()

        val spnInstitutes = findViewById<Spinner>(R.id.spnUniversities)
        val spnCareers = findViewById<Spinner>(R.id.spnCareers)
        val spnCourses = findViewById<Spinner>(R.id.spnCourses)
        val spnTopics = findViewById<Spinner>(R.id.spnTopics)

        //Institutes
        var listInstituteId = mutableListOf<Long>()
        var listInstituteName = mutableListOf<String>("Universidades")

        var listCareerId = mutableListOf<Long>()
        var listCareerName = mutableListOf<String>("Carreras")

        //Courses
        var listCoursesId = mutableListOf<Long>()
        var listCoursesName = mutableListOf<String>("Cursos")

        //Topics
        var listTopicsId = mutableListOf<Long>()
        var listTopicsName = mutableListOf<String>("Temas")

        //SE llenan los spinners -- para que se vean al inicio de la vista
        var adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listInstituteName)
        spnInstitutes.adapter = adatador

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCareerName)
        spnCareers.adapter = adatador

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCoursesName)
        spnCourses.adapter = adatador

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listTopicsName)
        spnTopics.adapter = adatador

        // AQUI COMIENZA LA MAGIA-------------------------------------------------------------
        val serviceInstitute = retrofit.create<InstituteService>(InstituteService::class.java)

        serviceInstitute.getAllInstitutes().enqueue(object : Callback<List<Institute>> {
            override fun onResponse(
                call: Call<List<Institute>>?,
                response: Response<List<Institute>>?
            ) {
                val institutes = response?.body()
                if (response != null) {
                    val institutes = response?.body()
                    for (i in institutes!!) {
                        listInstituteId.add(i.id)
                        listInstituteName.add(i.name)
                    }
                }
            }
            override fun onFailure(call: Call<List<Institute>>, t: Throwable) {
                t?.printStackTrace()
            }
        })



        val context = this

        spnInstitutes.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Log.d("Name", spnInstitutes.selectedItemPosition.toString() )
                if (spnInstitutes.selectedItemPosition > 0) {
                    listCareerId = getCareersAPI(listInstituteId.get(spnInstitutes.selectedItemPosition - 1))

                    adatador = ArrayAdapter(context, android.R.layout.simple_spinner_item, listCoursesName)
                    spnCourses.adapter = adatador
                    adatador = ArrayAdapter(context, android.R.layout.simple_spinner_item, listTopicsName)
                    spnTopics.adapter = adatador
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        spnCareers.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spnCareers.selectedItemPosition > 0) {
                    listCoursesId = getCoursesAPI(listCareerId.get(spnCareers.selectedItemPosition - 1))

                    adatador = ArrayAdapter(context, android.R.layout.simple_spinner_item, listTopicsName)
                    spnTopics.adapter = adatador
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }


        spnCourses.onItemSelectedListener=object:
            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (spnCourses.selectedItemPosition > 0) {
                    getTopicsAPI(listCoursesId.get(spnCourses.selectedItemPosition - 1))
                    // TODO IMPLEMENTACION DE ARCHIVOS ----> LISTO PARA TERMINARSE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }




    }

    fun getService (): Retrofit
    {
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://studydeskapi.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit

    }


    fun getCareersAPI(instituteId: Long) : MutableList<Long> {

        // AQUI COMIENZA LA MAGIA -------------------------------------------------------------
        //Careers
        var listCareerId = mutableListOf<Long>()
        var listCareerName = mutableListOf<String>("Carreras")
        val spnCareers = findViewById<Spinner>(R.id.spnCareers)
        var adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCareerName)
        spnCareers.adapter = adatador
        val retrofit = getService()


        val serviceCareer = retrofit.create<CareerService>(CareerService::class.java)

        serviceCareer.getCareersByInstituteId(instituteId).enqueue(object : Callback<List<Career>> {
            override fun onResponse(
                call: Call<List<Career>>?,
                response: Response<List<Career>>?
            ) {
                val careers = response?.body()
                if (response != null) {

                    for (i in careers!!) {
                        listCareerId.add(i.id)
                        listCareerName.add(i.name)
                    }
                }
            }
            override fun onFailure(call: Call<List<Career>>, t: Throwable) {
                t?.printStackTrace()
            }
        })

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCareerName)
        spnCareers.adapter = adatador
        return  listCareerId
        //AQUI ACABA------------------------------------------------------------------------
    }

    fun getCoursesAPI(careerId: Long) : MutableList<Long> {

        // AQUI COMIENZA LA MAGIA -------------------------------------------------------------
        //Courses
        var listCoursesId = mutableListOf<Long>()
        var listCoursesName = mutableListOf<String>("Cursos")
        val spnCourses = findViewById<Spinner>(R.id.spnCourses)
        var adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCoursesName)
        spnCourses.adapter = adatador
        val retrofit = getService()


        val serviceCourse = retrofit.create<CourseService>(CourseService::class.java)

        serviceCourse.getCoursesByCareerId(careerId).enqueue(object : Callback<List<Course>> {
            override fun onResponse(
                call: Call<List<Course>>?,
                response: Response<List<Course>>?
            ) {
                val courses = response?.body()
                if (response != null) {

                    for (i in courses!!) {
                        listCoursesId.add(i.id)
                        listCoursesName.add(i.name)
                    }
                }
            }
            override fun onFailure(call: Call<List<Course>>, t: Throwable) {
                t?.printStackTrace()
            }
        })

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listCoursesName)
        spnCourses.adapter = adatador
        return listCoursesId
        //AQUI ACABA------------------------------------------------------------------------
    }

    fun getTopicsAPI(CourseId: Long) : MutableList<Long> {

        // AQUI COMIENZA LA MAGIA -------------------------------------------------------------
        //Topics
        var listTopicsId = mutableListOf<Long>()
        var listTopicsName = mutableListOf<String>("Temas")
        val spnTopics = findViewById<Spinner>(R.id.spnTopics)
        var adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listTopicsName)
        spnTopics.adapter = adatador
        val retrofit = getService()


        val serviceTopic = retrofit.create<TopicService>(TopicService::class.java)

        serviceTopic.getTopicsByCourseId(CourseId).enqueue(object : Callback<List<Topic>> {
            override fun onResponse(
                call: Call<List<Topic>>?,
                response: Response<List<Topic>>?
            ) {
                val topics = response?.body()
                if (response != null) {

                    for (i in topics!!) {
                        listTopicsId.add(i.id)
                        listTopicsName.add(i.name)
                    }
                }
            }
            override fun onFailure(call: Call<List<Topic>>, t: Throwable) {
                t?.printStackTrace()
            }
        })

        adatador = ArrayAdapter(this, android.R.layout.simple_spinner_item, listTopicsName)
        spnTopics.adapter = adatador
        return listTopicsId
        //AQUI ACABA------------------------------------------------------------------------
    }
}