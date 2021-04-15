package com.example.vencarro

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vencarro.databinding.FragmentListaVeiculoBinding
import com.example.vencarro.databinding.LinhaListaBinding
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject

class ListaFragment : Fragment() {

    lateinit var binding:FragmentListaVeiculoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //antes do view binding
        //return inflater.inflate(R.layout.fragment_lista, container, false)

        binding = FragmentListaVeiculoBinding.inflate(inflater, container, false)
        return binding.root

    }

    // executa assim que a Activity que contem o Fragment for instanciada
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.rvListaVeiculo.layoutManager = LinearLayoutManager(context)
        binding.rvListaVeiculo.adapter = VeiculoAdapter(context)
    }

    // cria o adapter para a lista de veiculos
    // recebe o contexto do fragment como parametro
    class VeiculoAdapter(context:Context?):RecyclerView.Adapter<VeiculoHolder>() {

        // endpoint da tabela fipe que retornar as marcas de veiculo
        val URL_MARCA = "https://fipeapi.appspot.com/api/1/carros/marcas.json"
        // endpoint da tabela fipe contendo os modelos de veiculo de uma marca (incluir id marca)
        val URL_MODELO = "http://fipeapi.appspot.com/api/1/carros/veiculos/"
        // endpoint para obter os anos de fabricacao do modelo
        val URL_ANO = "http://fipeapi.appspot.com/api/1/carros/veiculo/"

        var listaVeiculo = ArrayList<VeiculoModel>()
        var queue:RequestQueue

        //armazena os dados do veiculo na medida em que e selecionado na lista
        var veiculoSelecionado:VeiculoModel

        // teste adicionando manualmente veiculos na lista...
        init {

            // cria a fila de requisições http
            queue = Volley.newRequestQueue(context)
            obterMarcasMock()
            //obterMarcas()
            veiculoSelecionado = VeiculoModel(0, "", 0, "", "", "")
            //listaVeiculo.add(VeiculoModel(1, "Fiat", 1, "Uno", "1", "2010"))
            //listaVeiculo.add(VeiculoModel(1, "Ford", 1, "Ranger", "1", "2015"))
            //listaVeiculo.add(VeiculoModel(1, "AUDIT", 1, "A5", "1", "2010"))

        }

        fun obterMarcasMock() {
            listaVeiculo.add(VeiculoModel(1, "Fiat", 0, "-", "", "-"))
            listaVeiculo.add(VeiculoModel(2, "Ford", 0, "-", "", "-"))
            listaVeiculo.add(VeiculoModel(3, "GM", 0, "-", "", "-"))

            notifyDataSetChanged()
        }

        fun obterModelosMock(id:Int) {
            listaVeiculo.clear()
            listaVeiculo.add(VeiculoModel(1, "Fiat", 1, "Toro", "", "-"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 2, "Uno", "", "-"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 3, "Strada", "", "-"))

            notifyDataSetChanged()
        }

        fun obterAnosMock(id:Int, id2:Int) {
            listaVeiculo.clear()
            listaVeiculo.add(VeiculoModel(1, "Fiat", 1, "Toro", "1", "1.6 Hibrido"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 2, "Toro", "2", "2.0 Alcool"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 3, "Toro", "3", "1.4 Gasolina"))

            notifyDataSetChanged()
        }

        fun obterprecoMock(id:Int, id2:Int, id3: String) {
            listaVeiculo.add(VeiculoModel(1, "Fiat", 1, "Toro", "1", "1.6 Hibrido"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 2, "Toro", "2", "2.0 Alcool"))
            listaVeiculo.add(VeiculoModel(1, "Fiat", 3, "Toro", "3", "1.4 Gasolina"))

            notifyDataSetChanged()
        }

        // requisicoes HTTP para obter os dados

        fun obterMarcas() {

            // adiciona uma requisicao HTTP na fila do volley
            queue.add(
                JsonArrayRequest(URL_MARCA,
                    Response.Listener<JSONArray> { response ->
                        for (i in 0 until response.length()) {
                            Log.i("FIPE", response.getJSONObject(i).toString())
                            val idMarca = response.getJSONObject(i).getInt("id")
                            val nomeMarca = response.getJSONObject(i).getString("fipe_name")
                            listaVeiculo.add(VeiculoModel(idMarca, nomeMarca, 0, "-", "", "-"))
                        }
                        // atualize a lista de veiculos na tela
                        notifyDataSetChanged()
                    },
                Response.ErrorListener { error ->
                    Log.e("FIPE", "ERRO: "+ error.message)
                })
            )
        }

        // obtem os modelos de veiculos de uma marca
        fun obterModelos(idMarca:Int) {

            val URL_FINAL = URL_MODELO + idMarca + ".json"
            //limpar a lista de veiculos com a carga das marcas
            listaVeiculo.clear()

            // adiciona uma requisicao HTTP na fila do volley
            queue.add(
                JsonArrayRequest(URL_FINAL,
                    Response.Listener<JSONArray> { response ->
                        for (i in 0 until response.length()) {
                            Log.i("FIPE", response.getJSONObject(i).toString())
                            val idModelo = response.getJSONObject(i).getInt("id")
                            val nomeModelo = response.getJSONObject(i).getString("fipe_name")
                            listaVeiculo.add(VeiculoModel(veiculoSelecionado.idMarca,
                                veiculoSelecionado.marca,
                                idModelo,
                                nomeModelo, "", "-"))
                        }
                        // atualize a lista de veiculos na tela
                        notifyDataSetChanged()
                    },
                    Response.ErrorListener { error ->
                        Log.e("FIPE", "ERRO: "+ error.message)
                    })
            )

        }

        // obtem os anos do modelo do veiculo de uma marca
        fun obterAnos(idMarca:Int, idModelo:Int) {

            val URL_FINAL = URL_ANO + idMarca + "/" + idModelo + ".json"
            //limpar a lista de veiculos com a carga das marcas e modelos
            listaVeiculo.clear()

            // adiciona uma requisicao HTTP na fila do volley
            queue.add(
                JsonArrayRequest(URL_FINAL,
                    Response.Listener<JSONArray> { response ->
                        for (i in 0 until response.length()) {
                            Log.i("FIPE", response.getJSONObject(i).toString())
                            val idAno = response.getJSONObject(i).getString("id")
                            val ano = response.getJSONObject(i).getString("name")
                            listaVeiculo.add(VeiculoModel(veiculoSelecionado.idMarca,
                                veiculoSelecionado.marca,
                                veiculoSelecionado.idModelo,
                                veiculoSelecionado.modelo,
                                idAno,
                                ano))
                        }
                        // atualize a lista de veiculos na tela
                        notifyDataSetChanged()
                    },
                    Response.ErrorListener { error ->
                        Log.e("FIPE", "ERRO: "+ error.message)
                    })
            )

        }

        // obtem o preco do veiculo (final)
        fun obterPreco(idMarca:Int, idModelo:Int, idAno:String) {

            val URL_FINAL = URL_ANO + idMarca + "/" + idModelo + "/" + idAno + ".json"

            // adiciona uma requisicao HTTP na fila do volley
            queue.add(
                JsonObjectRequest(URL_FINAL, null,
                    Response.Listener<JSONObject> { response ->
                        Log.i("FIPE", "Preco = " + response.getString("preco"))

                    },
                    Response.ErrorListener { error ->
                        Log.e("FIPE", "ERRO: "+ error.message)
                    })
            )

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiculoHolder {
            // antes do view binding
            //val layout = LayoutInflater.from(parent.context).inflate(R.layout.linha_lista, parent, false)

            //com o view binding
            val layout = LinhaListaBinding.inflate(LayoutInflater.from(parent.context), parent, false);
            return VeiculoHolder(layout)
        }

        // executa para cada elementos da lista (listaVeiculo)
        override fun onBindViewHolder(holder: VeiculoHolder, position: Int) {

            holder.txtMarca.text = listaVeiculo.get(position).marca
            holder.txtModelo.text = listaVeiculo.get(position).modelo
            holder.txtAno.text = listaVeiculo.get(position).ano

            // captura o evento de click sobre um item da lista
            holder.itemLista.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View?) {
                    Log.i("FIPE", listaVeiculo.get(position).marca)
                    //armazena o id e nome da marca selecionada
                    veiculoSelecionado = listaVeiculo.get(position)
                    if (veiculoSelecionado.idModelo == 0) {
                        obterModelosMock(veiculoSelecionado.idMarca)
                    } else if (veiculoSelecionado.idModelo != 0 && veiculoSelecionado.idAno == ""){
                        obterAnosMock(veiculoSelecionado.idMarca,
                        veiculoSelecionado.idModelo)
                    } else {
                        obterprecoMock(veiculoSelecionado.idMarca,
                        veiculoSelecionado.idModelo,
                        veiculoSelecionado.idAno)
                    }
                }

            })
        }

        // quantos veiculos existem na lista?
        override fun getItemCount(): Int {
            return listaVeiculo.size
        }

    }

    // cria variaveis que serao mapeadas para os elementos da interface (layout linha_lista.xml)
    class VeiculoHolder(itemView:LinhaListaBinding):RecyclerView.ViewHolder(itemView.root) {

        var txtMarca:TextView = itemView.txtMarca
        var txtModelo:TextView = itemView.txtModelo
        var txtAno:TextView = itemView.txtAno
        var itemLista:LinearLayout = itemView.itemLista

    }

}