package com.example.volley

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.example.taskmanager.ToDos

import com.example.volley.Volley.VolleySingleton

import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_home.layoutManager = LinearLayoutManager(this)
        fetchToDos()

    }


   fun fetchToDos() {

       val gson = Gson()

        val url = "https://jsonplaceholder.typicode.com/todos/"
        val request = JsonArrayRequest ( Request.Method.GET , url ,

            Response.Listener { response ->

                try {

                    var todos: ToDos
                    todos = gson.fromJson("$response", ToDos::class.java)

                    displayToDos(todos)

                }

                catch (e:Exception){ Log.e("inside try : test","Exception: $e" ) }


        }, Response.ErrorListener{ Log.e("testing query", "$it" ) })

        request.retryPolicy = DefaultRetryPolicy( DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 1f)

        VolleySingleton.getInstance(this).addToRequestQueue(request)

   }

    fun displayToDos (todos : ToDos) {
        rv_home.adapter = ToDosAdapter( this , todos , R.layout.rv_child_dashboard)
    }




}


