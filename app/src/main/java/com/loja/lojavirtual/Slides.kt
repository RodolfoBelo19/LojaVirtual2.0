package com.loja.lojavirtual

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide
import com.loja.lojavirtual.Form.FormLogin

class Slides : IntroActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_slides)

        isButtonBackVisible = false
        isButtonNextVisible = false

        addSlide(

            SimpleSlide.Builder()
                .background(R.color.Roxo)
                .image(R.drawable.drawer)
                .backgroundDark(R.color.Branco)
                .title("Loja Online de Calçados")
                .description("Entre e confira as promoções que preparamos para você!")
                .build()

        )

        addSlide(

            SimpleSlide.Builder()
                .background(R.color.AV)
                .title("Crie uma conta Grátis")
                .canGoBackward(false)
                .description("Cadastre-se agora mesmo! E venha conhecer os nossos produtos.")
                .build()
        )


    }

    override fun onPause() {
        super.onPause()

        var intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
    }
}
