package com.example.lutalivre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

// Activity = Tela
class MainActivity : AppCompatActivity() {

    // lateinit - instancio daqui a pouco...
    lateinit var lutador1:Lutador
    lateinit var lutador2:Lutador

    // executa ao criar a tela
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // desenha a tela conforme layout
        setContentView(R.layout.activity_main)

    }

    // reponder ao evento de click no botão Atacar
    fun atacar(view:View) {
        Log.i("LUTADOR", "id = ${view.id}")

        // codigo de inicialização
        // metodo tradicional para referenciar um elemento de interface
        //val atacante = findViewById<EditText>(R.id.edtAtacante)

        lutador1 = Lutador(edtAtacante.text.toString())
        lutador2 = Lutador(edtDefensor.text.toString())

        val resultado = lutador1.atacar(lutador2)
        //Toast.makeText(this, resultado, Toast.LENGTH_LONG).show()
        txtResultado.text = resultado

    }

}