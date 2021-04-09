package com.example.vencarro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vencarro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // antes do data binding
        //setContentView(R.layout.activity_main)

        // novo modelo com o data binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().
        add(R.id.flPrincipal, ListaFragment()).
        commit()

    }
}