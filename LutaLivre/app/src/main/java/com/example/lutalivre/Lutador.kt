package com.example.lutalivre

import android.util.Log
import java.util.Random

// Criar uma classe Lutador que possua o atributo nome no construtor primário e dois atributos de classe: ataque e defesa
class Lutador (var nome:String){

    var ataque = 0
    var defesa = 0

    // No bloco init atribuir um número aleatório entre 1 e 10 para ataque e outro para a defesa

    init {

        //Importar Random (import java.util.Random)
        //Sortear número (entre 1 e 10):
        val random = Random()
        this.ataque = random.nextInt(10) + 1
        this.defesa = random.nextInt(10) + 1

        Log.i("LUTADOR", "Ataque = ${this.ataque}, defesa  = ${this.defesa}")
    }

    // Criar um método atacar que receba como parâmetro um objeto Lutador (o adversário) que retornará o nome do vencedor conforme a regra:
    // Se atributo ataque > defesa  atacante ganhou
    // ataque == defesa  Empate
    // ataque < defesa  defensor (Lutador passado como parâmetro) ganhou

    fun atacar (inimigo:Lutador):String {

        if (this.ataque > inimigo.defesa) {
            return this.nome
        } else if (this.ataque == inimigo.defesa) {
            return "Empate"
        } else {
            return inimigo.nome
        }

    }


}