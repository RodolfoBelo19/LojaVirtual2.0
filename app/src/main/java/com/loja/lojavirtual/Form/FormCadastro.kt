package com.loja.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.loja.lojavirtual.R
import com.loja.lojavirtual.databinding.ActivityFormCadastroBinding
import kotlinx.android.synthetic.main.activity_form_cadastro.*



class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.btCadastrar.setOnClickListener {
            CadastrarUsuario()
        }

    }

    private fun CadastrarUsuario(){

        val editEmail = binding.editEmail
        val editSenha = binding.editSenha
        val email = editEmail.text.toString()
        val senha = editSenha.text.toString()

        if(email.isEmpty() || senha.isEmpty()){

            var snackbar = Snackbar.make(binding.layoutCadastro,"Coloque o seu e-mail e sua senha!",Snackbar.LENGTH_INDEFINITE)
                .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

                })
            snackbar.show()

        }else{

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener {

                if (it.isSuccessful){
                    var snackbar = Snackbar.make(binding.layoutCadastro,"Cadastro Realizado com Sucesso!",Snackbar.LENGTH_INDEFINITE)
                        .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {
                             VoltarParaFormLogin()
                        })
                    snackbar.show()
                }
            }.addOnFailureListener {

                var snackbar = Snackbar.make(binding.layoutCadastro,"Erro ao cadastrar usuário",Snackbar.LENGTH_INDEFINITE)
                    .setBackgroundTint(Color.WHITE).setTextColor(Color.BLACK).setAction("OK", View.OnClickListener {

                    })
                snackbar.show()
            }
        }
    }

    private fun VoltarParaFormLogin(){

        var intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
        finish()
    }


}
