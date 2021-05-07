package com.example.trabalhoandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var queue:RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        queue = Volley.newRequestQueue(this)

    }




    fun onClickBotao(view:View) {

        var param = JSONObject()
        param.put("usuario", "teste123")

        val URL = "https://7c2bad50.us-south.apigw.appdomain.cloud/api/placar"
        val placar = JsonObjectRequest(Request.Method.POST,
                URL,
                param,
                {response -> Log.i("TRABALHO", response.toString())},
                {error -> Log.e("TRABALHO", error.toString())}
        )

        // adicionar o request na fila...
        queue.add(placar)


    }
}