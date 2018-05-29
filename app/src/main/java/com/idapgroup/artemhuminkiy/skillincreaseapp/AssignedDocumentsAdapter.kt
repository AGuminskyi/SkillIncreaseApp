package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class AssignedDocumentsAdapter: RecyclerView.Adapter<AssignedDocumentsAdapter.MyViewHolder>() {

    private var asignedDocuments = mutableListOf<Repository>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.asigned_documents, parent, false)
    }

    override fun getItemCount(): Int {
        //TODO
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //TODO
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}
