package com.example.vencarro

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.example.vencarro.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // arquivo da imagem (foto)
    lateinit var arquivoImagem: File

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

    fun exibeResumo(veiculoSelecionado: VeiculoModel) {

        Log.i("VEICULOSELECIONADO", veiculoSelecionado.toString())
        // parametros a serem enviados ao ResumoFragment
        val bundle = Bundle()
        bundle.putString("MARCA", veiculoSelecionado.marca)
        bundle.putString("MODELO", veiculoSelecionado.modelo)
        bundle.putString("ANO", veiculoSelecionado.ano)
        bundle.putString("PRECO", veiculoSelecionado.preco)

        val resumo = ResumoFragment()
        resumo.arguments = bundle

        supportFragmentManager.beginTransaction().
        replace(R.id.flPrincipal, resumo).
        commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            val bmp = data!!.extras!!.get("data") as Bitmap
            // referencio o ImageView declarado no ResumoFragment
            val preVizImagem = findViewById<ImageView>(R.id.imgFotoResumo)
            preVizImagem.setImageBitmap(bmp)

            // salva a imagem localmente
            arquivoImagem = File.createTempFile("TMP", ".jpg", cacheDir)

            Log.i("ARQUIVO_IMAGEM", arquivoImagem.absolutePath)

            val output = FileOutputStream(arquivoImagem)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, output)
            output.flush()
            output.close()

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}