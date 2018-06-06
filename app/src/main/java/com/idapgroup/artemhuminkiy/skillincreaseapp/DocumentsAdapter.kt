package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.gitHub.Repository

class DocumentsAdapter(private val onDoneClick: (Repository) -> Unit) : RecyclerView.Adapter<DocumentsAdapter.MyViewHolder>() {

    private var repos : MutableList<Repository> = mutableListOf()

    private val usersList = mutableListOf("Гуминский Артем Николаевич",
            "Кузьминых Валерий Олександрович", "Аушева Наталия Николаевна",
            "Ляшенко Максим Владимирович", "Мамалыга Владимир Михайлович")


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repository = repos[position]
        holder.documentType.text = holder.itemView.context.getString(R.string.document_type, repository.id)
//        holder.author.text = holder.itemView.context.getString(R.string.author, repository.owner.login)
        holder.author.text = holder.itemView.context.getString(R.string.author, getName(0,4))
        holder.documentName.text = holder.itemView.context.getString(R.string.document_name, repository.name)
        holder.executionTime.text = ("Дней до истечения срока: " + getRandomValue(1, 30))
        holder.done.setOnClickListener {
            onDoneClick(repos[position])
            repos.removeAt(position)
            notifyDataSetChanged()
        }
        setJobProgressText(holder.jobProgress)
    }

    private fun setJobProgressText(jobProgress: TextView) {
        val value = getRandomValue(1,3)
        if(value == 1){
            jobProgress.text = "Выполнено"
            jobProgress.setTextColor(jobProgress.context.resources.getColor(R.color.green))
        }
        if(value == 2){
            jobProgress.text = "В процессе"
            jobProgress.setTextColor(jobProgress.context.resources.getColor(R.color.colorAccent))
        }
        if(value == 3){
            jobProgress.text = "Не рассмотрено"
            jobProgress.setTextColor(jobProgress.context.resources.getColor(R.color.red))
        }
    }

    private fun getName(start: Int, end: Int): String{
        return usersList[getRandomValue(start,end)]
    }

    private fun getRandomValue(start: Int, end: Int): Int {
        return randBetween(start,end)
    }

    private fun randBetween(start: Int, end: Int): Int{
        return start + Math.round(Math.random() * (end - start)).toInt()
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
        val executionTime: TextView = itemView.findViewById(R.id.execution_time)
        val jobProgress: TextView = itemView.findViewById(R.id.jobProgress)
    }

    enum class EXECTUTION_COLOR{
        DONE, IN_PROGRESS, NOT_TAKEN
    }
}