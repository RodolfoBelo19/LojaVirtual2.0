package com.loja.lojavirtual.Fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.dialog.MaterialDialogs
import com.google.firebase.firestore.FirebaseFirestore
import com.loja.lojavirtual.Model.Dados

import com.loja.lojavirtual.R
import com.loja.lojavirtual.databinding.FragmentProdutosBinding
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_produtos.*
import kotlinx.android.synthetic.main.lista_produtos.view.*
import kotlinx.android.synthetic.main.pagamento.*


class Produtos : Fragment() {

    private lateinit var Adapter: GroupAdapter<ViewHolder>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_produtos, container, false)
    }

    private var fragmentProdutos: FragmentProdutosBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val bindingProdutos = FragmentProdutosBinding.bind(view)
        fragmentProdutos = bindingProdutos

        val recycler_produtos = bindingProdutos.recyclerProdutos

        Adapter = GroupAdapter()
        recycler_produtos.adapter = Adapter
        Adapter.setOnItemClickListener { item, view ->
            Toast.makeText(context, "Item Clicado", Toast.LENGTH_SHORT).show()
        }

        BuscarProdutos()

    }

    private inner class ProdutosItem(internal val adProdutos: Dados) : Item<ViewHolder>() {

        override fun getLayout(): Int {
            return R.layout.lista_produtos

        }

        override fun bind(viewHolder: ViewHolder, position: Int) {

            viewHolder.itemView.findViewById<TextView>(R.id.nomeProduto).text = adProdutos.nome
            viewHolder.itemView.findViewById<TextView>(R.id.precoProduto).text = adProdutos.preco
            var fotoProduto = viewHolder.itemView.findViewById<ImageView>(R.id.fotoProduto)
            Picasso.get().load(adProdutos.uid).into(viewHolder.itemView.fotoProduto)
        }
    }


        private fun BuscarProdutos() {

            FirebaseFirestore.getInstance().collection("Produtos")
    .addSnapshotListener { snapshot, exception ->
        exception?.let {
            return@addSnapshotListener
        }

        snapshot?.let {

            for (doc in snapshot) {
                val produtos = doc.toObject(Dados::class.java)
                Adapter.add(ProdutosItem(produtos))
            }
        }

    }
}

    }

