package com.example.tabuada

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var num1 = 0
    var num2 = 0
    var placar = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sortear()
        txtResposta.text = "0"

        sbValor.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i("TABUADA", "Valor: ${progress}")
                txtResposta.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })
    }

    fun sortear() {

        num1 = Random.nextInt(1, 9)
        num2 = Random.nextInt(1, 9)

        txtNum1.text = num1.toString()
        txtNum2.text = num2.toString()

    }

    fun resposta (view:View) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Resultado")
        var resultado = ""

        if (sbValor.progress == (num1 * num2)) {
            resultado = getString(R.string.msgResposta, getString(R.string.lblCorreto))
            placar++
            if (placar > 10) {
                placar = 0
            }
            rbPontos.rating = placar.toFloat()
        } else {
            resultado = getString(R.string.msgResposta, getString(R.string.lblErrado))
        }

        builder.setMessage(resultado)
        builder.setPositiveButton(android.R.string.ok, object: DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog!!.cancel()
                sortear()
            }

        }

        )
        builder.show()

    }


}