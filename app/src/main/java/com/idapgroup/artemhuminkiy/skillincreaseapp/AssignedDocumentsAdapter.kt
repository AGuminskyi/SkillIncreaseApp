package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class AssignedDocumentsAdapter: RecyclerView.Adapter<AssignedDocumentsAdapter.MyViewHolder>() {

    private var asignedDocuments = mutableListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.documents_list_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int = asignedDocuments.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val assignedDocument = asignedDocuments[position]
        holder.documentAssigneData.text = "30.05.18"
        holder.documentId.text = assignedDocument.id
        holder.documentName.text = assignedDocument.owner.login
    }

    fun addRepos(items : List<Repository>){
        asignedDocuments.addAll(items)
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val documentId: TextView = itemView.findViewById(R.id.document_id_field)
        val documentName : TextView = itemView.findViewById(R.id.document_name_field)
        val documentAssigneData : TextView = itemView.findViewById(R.id.document_assigned_time_field)
    }
}
