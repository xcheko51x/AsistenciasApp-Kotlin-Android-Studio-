package com.xcheko51x.asistenciasapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RegistrosAdapter(
    val listaRegistros: ArrayList<String>,
    val tvTotalRegistros: TextView
): RecyclerView.Adapter<RegistrosAdapter.ViewHolder>() {

    private var onClick: OnItemClicked ?= null

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val btnBorrar: ImageButton = itemView.findViewById(R.id.btnBorrar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistrosAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv_registro, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistrosAdapter.ViewHolder, position: Int) {
        val registro = listaRegistros[position]

        tvTotalRegistros.text = "${listaRegistros.size} Registros"

        holder.tvNombre.text = registro

        holder.btnBorrar.setOnClickListener {
            onClick?.borrarRegistro(registro)
        }
    }

    override fun getItemCount(): Int {
        return listaRegistros.size
    }

    interface OnItemClicked {
        fun borrarRegistro(nombre: String)
    }

    fun setOnClick(onClick: OnItemClicked) {
        this.onClick = onClick
    }
}