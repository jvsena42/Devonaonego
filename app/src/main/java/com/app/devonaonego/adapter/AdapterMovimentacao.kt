package com.app.devonaonego.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.devonaonego.R
import com.app.devonaonego.model.Movimentacao

/**
 * Created by Joao Victor
 */

class AdapterMovimentacao(var movimentacoes: List<Movimentacao>, internal var context: Context) :
    RecyclerView.Adapter<AdapterMovimentacao.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLista = LayoutInflater.from(parent.context).inflate(R.layout.adapter_movimentacao, parent, false)
        return MyViewHolder(itemLista)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (_, categoria, descricao, tipo, valor) = movimentacoes[position]

        holder.titulo.text = descricao
        holder.valor.text = valor.toString()
        holder.categoria.text = categoria

        holder.valor.setTextColor(context.resources.getColor(R.color.colorAccent))

        if (tipo == "d") {
            holder.valor.setTextColor(context.resources.getColor(R.color.colorAccent))
            holder.valor.text = "-$valor"
        }
    }


    override fun getItemCount(): Int {
        return movimentacoes.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var titulo: TextView
        internal var valor: TextView
        internal var categoria: TextView

        init {

            titulo = itemView.findViewById(R.id.textAdapterTitulo)
            valor = itemView.findViewById(R.id.textAdapterValor)
            categoria = itemView.findViewById(R.id.textAdapterCategoria)
        }

    }

}
