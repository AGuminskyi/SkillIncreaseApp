package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class DocumentsAdapter(private val onDoneClick: () -> Unit) : RecyclerView.Adapter<DocumentsAdapter.MyViewHolder>() {

    private var repos : MutableList<Repository> = mutableListOf()

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repository = repos[position]
        holder.documentType.text = holder.itemView.context.getString(R.string.document_type, repository.id)
        holder.author.text = holder.itemView.context.getString(R.string.author, repository.owner.login)
        holder.documentName.text = holder.itemView.context.getString(R.string.document_name, repository.name)
        holder.done.setOnClickListener {
            repos.removeAt(position)
            onDoneClick()
            notifyDataSetChanged()
        }
    }

    fun addRepos(items : List<Repository>){
        repos.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = repos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.files_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val documentType : TextView = itemView.findViewById(R.id.document_type)
        val author : TextView = itemView.findViewById(R.id.authorTextView)
        val documentName : TextView = itemView.findViewById(R.id.document_name)
        val done : Button = itemView.findViewById(R.id.done)
    }
}