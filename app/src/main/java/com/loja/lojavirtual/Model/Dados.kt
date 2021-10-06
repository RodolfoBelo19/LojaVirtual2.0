package com.loja.lojavirtual.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Dados(

    val uid: String = " ",
    val nome: String = " ",
    val preco: String = " ",
    val url: String = " "):Parcelable