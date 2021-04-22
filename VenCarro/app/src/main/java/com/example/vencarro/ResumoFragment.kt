package com.example.vencarro

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.cloudant.sync.documentstore.DocumentBodyFactory
import com.cloudant.sync.documentstore.DocumentRevision
import com.cloudant.sync.documentstore.DocumentStore
import com.cloudant.sync.documentstore.UnsavedFileAttachment
import com.cloudant.sync.replication.ReplicatorBuilder
import com.example.vencarro.databinding.FragmentResumoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.net.URI

class ResumoFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: FragmentResumoBinding
    var marca =""
    var modelo = ""
    var ano = ""
    var preco = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        marca = arguments!!.getString("MARCA").toString()
        modelo = arguments!!.getString("MODELO").toString()
        ano = arguments!!.getString("ANO").toString()
        preco = arguments!!.getString("PRECO").toString()

        Log.i("RESUMO", marca + ", " + modelo + ", " + ano + ", " + preco)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResumoBinding.inflate(inflater, container, false)
        binding.txtMarcaResumo.text = marca
        binding.txtModeloResumo.text = modelo
        binding.txtAnoResumo.text = ano
        binding.txtPrecoResumo.text = preco
        binding.navMenu.setOnNavigationItemSelectedListener(this)
        return binding!!.root

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.btnFoto) {

            // acionar a camera
            val toCamera = Intent (MediaStore.ACTION_IMAGE_CAPTURE)
            // confirma que o Intent para a camera sera atendido
            if (toCamera.resolveActivity(activity!!.packageManager) != null) {
                activity!!.startActivityForResult(toCamera, 1)
            }

        } else {

            // criar o documento na cloud

            val ds = DocumentStore.getInstance(File("${context!!.filesDir.absoluteFile.absolutePath}", "vencar_datastore"))
            val revision = DocumentRevision()

            val body = HashMap<String, Any>()
            body["MARCA"] = marca
            body["MODELO"] = modelo
            body["ANO"] = ano
            body["PRECO"] = preco

            revision.body = DocumentBodyFactory.create(body)
            val saved = ds.database().create(revision)

            // anexar a imagem da foto ao documento
            // activity referencia a Activity que contém o Fragment para obter o arquivo da imagem (foto)
            val imagem = (activity as MainActivity).arquivoImagem
            val att1 = UnsavedFileAttachment(imagem, "image/jpeg")
            saved.attachments[imagem.name] = att1

            // atualiza o datastore (localmente)
            ds.database().update(saved)

            // iniciar a sincronização

            val uri = URI("https://13c9925a-139c-4d54-a991-4d57d27137bd-bluemix.cloudantnosqldb.appdomain.cloud/vencar")
            val replicator = ReplicatorBuilder.push().from(ds)
                    .to(uri)
                    .iamApiKey("rwslhAHvtPLWxwDw5W-vUkTuNMdlkRRXjOUioz_v4d2z")
                    .build()

            replicator.start()

        }
        return true
    }

}