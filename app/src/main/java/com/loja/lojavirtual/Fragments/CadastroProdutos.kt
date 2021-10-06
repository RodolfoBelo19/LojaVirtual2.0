package com.loja.lojavirtual.Fragments


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.loja.lojavirtual.Model.Dados
import com.loja.lojavirtual.R
import com.loja.lojavirtual.databinding.ActivityCadastroProdutosBinding
import kotlinx.android.synthetic.main.activity_cadastro_produtos.*
import kotlinx.android.synthetic.main.activity_cadastro_produtos.view.*
import java.util.*

class CadastroProdutos : AppCompatActivity() {

    private var SelecionarUri: Uri? = null
    private lateinit var binding: ActivityCadastroProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bt_selecionar_foto      = binding.btSelecionarFoto
        val bt_cadastrar_produto    = binding.btCadastrarProduto

        bt_selecionar_foto.setOnClickListener {

            SelecionarFotoDaGaleria()
        }

        bt_cadastrar_produto.setOnClickListener {

            SalvarDadosNoFirebase()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val foto_produto            = binding.fotoProduto
        val botao_selecionar_foto   = binding.btSelecionarFoto

        if(requestCode == 0){
          SelecionarUri = data?.data

          try {
              foto_produto.setImageURI(SelecionarUri)
              botao_selecionar_foto.alpha = 0f
          }catch (e: Exception) {
              e.printStackTrace()
          }
        }
    }

    private fun SelecionarFotoDaGaleria(){

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent,0)
    }

    private fun SalvarDadosNoFirebase(){

        val nomeArquivo = UUID.randomUUID().toString()
        val referencia = FirebaseStorage.getInstance().getReference(
            "/imagens/${nomeArquivo}")

        SelecionarUri?.let {

            referencia.putFile(it)
                .addOnSuccessListener {
                    referencia.downloadUrl.addOnSuccessListener {

                        val url = it.toString()
                        val nome = binding.editNome.text.toString()
                        val preco = binding.editPreco.text.toString()
                        val uid = FirebaseAuth.getInstance().uid

                        val Produtos = Dados(url,nome,preco)
                        FirebaseFirestore.getInstance().collection("Produtos")
                            .add(Produtos)
                            .addOnSuccessListener {

                                Toast.makeText(this,"Produto cadastrado com sucesso!",Toast.LENGTH_SHORT).show()

                            }.addOnFailureListener {

                            }


                    }
                }
        }

    }

}
