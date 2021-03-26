package com.example.vencarro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_lista.*
import kotlinx.android.synthetic.main.linha_lista.view.*

class ListaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_lista, container, false)

    }

    // executa assim que a Activity que contem o Fragment for instanciada
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvListaVeiculo.layoutManager = LinearLayoutManager(context)
        rvListaVeiculo.adapter = VeiculoAdapter()
    }

    // cria o adapter para a lista de veiculos

    class VeiculoAdapter():RecyclerView.Adapter<VeiculoHolder>() {

        var listaVeiculo = ArrayList<VeiculoModel>()

        // teste adicionando manualmente veiculos na lista...
        init {

            listaVeiculo.add(VeiculoModel(1, "Fiat", 1, "Uno", "1", "2010"))
            listaVeiculo.add(VeiculoModel(1, "Ford", 1, "Ranger", "1", "2015"))

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiculoHolder {
            val layout = LayoutInflater.from(parent.context).inflate(R.layout.linha_lista, parent, false)
            return VeiculoHolder(layout)
        }

        // executa para cada elementos da lista (listaVeiculo)
        override fun onBindViewHolder(holder: VeiculoHolder, position: Int) {

            holder.txtMarca.text = listaVeiculo.get(position).marca
            holder.txtModelo.text = listaVeiculo.get(position).modelo
            holder.txtAno.text = listaVeiculo.get(position).ano
        }

        // quantos veiculos existem na lista?
        override fun getItemCount(): Int {
            return listaVeiculo.size
        }

    }

    // cria variaveis que serao mapeadas para os elementos da interface (layout linha_lista.xml)
    class VeiculoHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        var txtMarca:TextView = itemView.txtMarca
        var txtModelo:TextView = itemView.txtModelo
        var txtAno:TextView = itemView.txtAno

    }

}