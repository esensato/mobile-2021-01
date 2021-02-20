package com.example.memocolors

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    lateinit var sorteados:IntArray
    lateinit var timer:Timer
    var repeticoes = 1
    var pos = 0
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtRepeticoes.text = "$repeticoes"
        handler = Handler(mainLooper)

    }



    fun iniciar(view:View) {

        //(view as Button).isEnabled = false

        sorteados = sortear()
        pos = 0
        timer = Timer()
        var apagar = true
        var corOriginal = 0
        timer.schedule(object: TimerTask() {
               override fun run() {
                   if (pos == sorteados.size) {
                       timer.cancel()
                       pos = 0
                       Log.i("SORTEIO", "Cancelado timer")
                   } else {
                       Log.i("SORTEIO", "pos = ${sorteados.get(pos)}")
                       if (apagar) {
                           val btn = obterBotao(sorteados.get(pos))
                           corOriginal = (btn.background as ColorDrawable).color
                           btn.setBackgroundColor(Color.WHITE)
                           apagar = false

                           handler.post {
                               Toast.makeText(baseContext, "Oi", Toast.LENGTH_LONG).show()

                           }
                       } else {
                           try {

                               obterBotao(sorteados.get(pos)).setBackgroundColor(corOriginal)
                               apagar = true
                               pos++

                           } catch (e:Exception) {
                               handler.post {
                                   val builder = AlertDialog.Builder(baseContext)
                                   builder.setTitle(R.string.app_name)
                                   builder.setIcon(R.drawable.baseline_error_black_18dp)
                                   builder.setMessage(e.message)
                                   builder.show()

                               }
                           }
                       }

                   }
               }
               }, 0, 1000)


    }

    fun sortear():IntArray {

        var ret = IntArray(repeticoes)
        for (i in 0..ret.size - 1) {
            ret[i] = Random.nextInt(3)
        }

        return ret
    }

    fun obterBotao(numero:Int):ImageButton {
        val ret = when (numero) {
            0 -> btnCima
            1 -> btnDireita
            2 -> btnBaixo
            else -> btnEsquerda
        }

        return ret
    }

    fun selecionar(view:View) {

        if (view.id == obterBotao(sorteados[pos]).id) {
            Log.i("SELECIONAR", "Correto")
            pos++
            if (pos == sorteados.size) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.app_name)
                builder.setIcon(R.drawable.baseline_check_circle_black_18dp)
                builder.setMessage(R.string.lblCorretoDialog)
                builder.show()
                repeticoes++
                pos = 0
                btnIniciar.isEnabled = true
                txtRepeticoes.text = "$repeticoes"
            }
        } else {
            Log.i("SELECIONAR", "Errado")
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setIcon(R.drawable.baseline_error_black_18dp)
            builder.setMessage(R.string.lblErroDialog)
            builder.show()
            repeticoes = 1
            pos = 0
            btnIniciar.isEnabled = true
            txtRepeticoes.text = "$repeticoes"
        }



    }
}