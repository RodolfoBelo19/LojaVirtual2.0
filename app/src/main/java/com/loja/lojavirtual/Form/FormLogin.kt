package com.loja.lojavirtual.Form

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.loja.lojavirtual.R
import com.loja.lojavirtual.TelaPrincipal
import com.loja.lojavirtual.databinding.ActivityFormLoginBinding
import kotlinx.android.synthetic.main.activity_form_login.*

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        VerificarUsuarioLogado()

        supportActionBar!!.hide()

        val text_tela_cadastro  = binding.textTelaCadastro
        val bt_entrar           = binding.btEntrar

        text_tela_cadastro.setOnClickListener {
            var intent = Intent(this,FormCadastro::class.java)
            startActivity(intent)
        }

        bt_entrar.setOnClickListener {
            AutenticarUsuario()
        }
    }

    private fun AutenticarUsuario(){

        val editEmail = binding.editEmail
        val editSenha = binding.editSenha
        val email = editEmail.text.toString()
        val senha = editSenha.text.toString()

        if (email.isEmpty() || senha.isEmpty()){
            var snackbar = Snackbar.make(binding.layoutLogin,"Coloque um e-mail e uma senha!",Snackbar.LENGTH_INDEFINITE).setBackgroundTint(
                    Color.WHITE).setTextColor(Color.BLACK)
                .setAction("OK", View.OnClickListener {

                })
            snackbar.show()
        }else{

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener {

                if (it.isSuccessful){
                      frameL.visibility = View.VISIBLE
                      Handler().postDelayed({AbrirTelaPrincipal()},3000)

                }


            }.addOnFailureListener {

                var snackbar = Snackbar.make(binding.layoutLogin,"Erro ao logar usuário!",Snackbar.LENGTH_INDEFINITE).setBackgroundTint(
                        Color.WHITE).setTextColor(Color.BLACK)
                    .setAction("OK", View.OnClickListener {

                    })
                snackbar.show()
            }
        }
    }

    private fun VerificarUsuarioLogado(){

        val usuarioAtual = FirebaseAuth.getInstance().currentUser

        if (usuarioAtual != null){
            AbrirTelaPrincipal()
        }
    }

    private fun AbrirTelaPrincipal(){

        var intent = Intent(this,TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}
