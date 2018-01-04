package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.User

class MyRecyclerAdapter : RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>() {

    private var users : MutableList<User> = mutableListOf()
    private var repos : MutableList<Repository> = mutableListOf()

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
//        val user = users[position]
//        holder!!.id.text = user.id
//        holder.name.text = user.name
//        holder.age.text = user.age.toString()
//        holder.gender.text = user.gender

        val repository = repos[position]
        holder!!.name.text = repository.name
        holder.gender.text = repository.full_name
    }

    fun addRepos(items : List<Repository>){
        repos.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: List<User>){
        users.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = repos.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent!!.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id : TextView = itemView.findViewById(R.id.id)
        var name : TextView = itemView.findViewById(R.id.name)
        var age : TextView = itemView.findViewById(R.id.age)
        var gender : TextView = itemView.findViewById(R.id.gender)

    }
}