package com.app.devonaonego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.devonaonego.R
import com.app.devonaonego.model.Movimentacao
import kotlinx.android.synthetic.main.adapter_movimentacao.view.*

class AdapterMovim (private val movimentacoes: List<Movimentacao>,
                    private val context: Context): RecyclerView.Adapter<AdapterMovim.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_movimentacao,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movimentacoes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movimentacao = movimentacoes[position]

        holder?.let{
            it.titulo.text = movimentacao.descricao
            it.valor.text = movimentacao.valor.toString()
            it.categoria.text = movimentacao.categoria
        }

    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titulo = itemView.textAdapterTitulo
        val valor = itemView.textAdapterValor
        val categoria = itemView.textAdapterCategoria

    }

}
