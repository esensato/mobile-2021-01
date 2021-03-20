package com.example.memocolors

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    // contém a sequencia de cores sortedas
    lateinit var sorteados:IntArray
    // timer que controla o tempo entre sorteios
    lateinit var timer:Timer
    // quantidade de cores sorteadas
    var repeticoes = 1
    // posição do item sorteado no array sorteados
    var pos = 0

    var corTouch = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtRepeticoes.text = repeticoes.toString()
        desabilitarBotoes(true)

        // registra o evento on touch para todos os botões de cor
        val btns = arrayOf(btnCima, btnDireita, btnBaixo, btnEsquerda);
        for (btn in btns) {

            btn.setOnTouchListener(object: View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {

                    Log.i("MEMOCOLORS", "Acao: ${event!!.action}")

                    if (event!!.action == MotionEvent.ACTION_DOWN) {

                        corTouch = ((v!!.background as ColorDrawable).color)
                        v!!.setBackgroundColor(Color.GRAY)

                    } else if (event!!.action == MotionEvent.ACTION_UP) {

                        v!!.setBackgroundColor(corTouch)
                        selecionar(v)
                    }

                    return true
                }

            })
        }


    }

    fun desabilitarBotoes(desabilitar:Boolean) {

        runOnUiThread {

            btnCima.isEnabled = !desabilitar
            btnDireita.isEnabled = !desabilitar
            btnBaixo.isEnabled = !desabilitar
            btnEsquerda.isEnabled = !desabilitar

        }
    }

    fun iniciar(view:View) {

        // Acionar a função sortear() e armazenar o resultado na variável sorteados
        sorteados = sortear()
        //Iniciar a variável pos com 0
        pos = 0
        //Iniciar a variável timer com uma nova instância
        timer = Timer()
        desabilitarBotoes(true)

        // Declarar duas varáveis: apagar definida como true (irá controlar se o botão deve ser "apagado"
        // ou retornar à cor original (efeito "piscar") e corOriginal para armazenar qual a cor original
        // do botão que será "apagado"
        var apagar = true
        var corOriginal = 0
        var contexto = this;

        //Efetuar um agendamento (schedule) para execução de uma função a cada 1s
        timer.schedule(object :TimerTask() {
            override fun run() {
                // Se a posição do botão sorteado for igual ao tamanho do array sorteados:
                if (pos == sorteados.size) {

                    //Cancelar o timer (timer.cancel())
                    timer.cancel()
                    // retorna pos para o inicio das cores sorteadas para que o jogador
                    // tente adivinhar a sequencia
                    pos = 0
                    desabilitarBotoes(false)

                    runOnUiThread {
                        Toast.makeText(contexto, "Sua tentativa", Toast.LENGTH_SHORT).show()

                    }

                } else {
                    // Armazenar em uma variável btn o botão que foi sorteado na posição
                    // pos (função obterBotao) dentro do array sorteados
                    var btn = obterBotao(sorteados[pos])

                    //Verifica se o valor da variável apagar e se true:
                    if (apagar) {
                        //Armazenar a cor original do botão na variável corOriginal
                        corOriginal = ((btn.background as ColorDrawable).color)
                        //Alterar a cor do botão da vez para branco
                        //Envia requisicao de alteracao para o Main Thread
                        runOnUiThread {
                            btn.setBackgroundColor(Color.WHITE)
                        }
                        //Retornar o valor de apagar para false
                        apagar = false

                    } else {
                        //Retornar a cor do botão para corOriginal
                        runOnUiThread {
                            btn.setBackgroundColor(corOriginal)
                        }
                        //Definir apagar como true e atualizar pos para processar
                        // o próximo botão sorteado do array
                        pos++
                        apagar = true

                    }
                }

            }

        }, 0, 1000)


    }

    fun selecionar (view:View) {
        //Obter o id do botão clicado (via parâmetro view)
        var idBotaoClicado = view.id
        // Verificar se o id do botão clicado corresponde ao id do botão na sequencia sorteada
        var botaoDaVez = obterBotao(sorteados[pos])
        // acertou a sequencia da cor na posicao
        if (botaoDaVez.id == idBotaoClicado) {
            // Incrementar a posição na sequencia (pos) para a próxima cor
            pos++
            // Verificar se a a nova posição (pos) corresponde à quantidade de itens sorteados na rodada
            // e caso positivo, exibir alerta com a mensagem "Sequência correta!",
            // atualizar o nível de dificuldade (variável repeticoes) e retornar pos para 0
            if (pos == sorteados.size) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.app_name)
                builder.setMessage(R.string.lblCorretoDialog)
                builder.show()
                repeticoes++
                txtRepeticoes.text = repeticoes.toString()
                pos = 0
            }

        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage(R.string.lblErroDialog)
            builder.show()
            pos = 0
        }

    }

    fun sortear():IntArray {
        var ret = IntArray(repeticoes)
        // loop de 0 ate a quantidade de repeticoes
        for (p in 0..repeticoes - 1) {
            // importar de kotlin.random.Random (sorteia entra 0 e 3
            ret[p] = Random.nextInt(4)
            Log.i("MEMOCOLORS", ret[p].toString())
        }
        return ret;
    }

    fun obterBotao(pos:Int):ImageButton {
        return when(pos) {
            0 -> btnCima
            1 -> btnDireita
            2 -> btnBaixo
            else -> btnEsquerda
        }
    }
}