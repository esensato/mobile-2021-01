package com.example.vencarro

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //oculta a action bar
        supportActionBar!!.hide()

        Log.i("VENCARRO", "API ${Build.VERSION.SDK_INT}")

        //oculta barra superior
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        // executa o metodo iniciar apos 5s (5000)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            iniciar()
        }, 5000)

    }

    fun iniciar() {
        // chamar a MainActivity
        var toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
        // encerra a SplashActivity
        finish()
    }
}