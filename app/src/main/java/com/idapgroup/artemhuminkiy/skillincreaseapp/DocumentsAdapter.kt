package com.idapgroup.artemhuminkiy.skillincreaseapp

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.idapgroup.artemhuminkiy.skillincreaseapp.dialogs.PhotoDialogFragment
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.DocumentFile
import com.idapgroup.artemhuminkiy.skillincreaseapp.userData.PROCESSING_STATE

class DocumentsAdapter(private val onDoneClick: (DocumentFile) -> Unit,
                       private val onOpenFile: (String?) -> Unit,
                       private val onPhotoClick: (String?) -> Unit) : RecyclerView.Adapter<DocumentsAdapter.MyViewHolder>() {

    private var repos: MutableList<DocumentFile> = mutableListOf()

    private val usersList = mutableListOf("Гуминский Артем Николаевич",
            "Кузьминых Валерий Олександрович", "Аушева Наталия Николаевна",
            "Ляшенко Максим Владимирович", "Мамалыга Владимир Михайлович")


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val repository = repos[position]
        holder.documentType.text = holder.itemView.context.getString(R.string.document_type, repository.id)
//        holder.author.text = holder.itemView.context.getString(R.string.author, repository)
        holder.author.text = holder.itemView.context.getString(R.string.author, getName(0, 4))
        holder.documentName.text = holder.itemView.context.getString(R.string.document_name, repository.fileName)
        holder.executionTime.text = ("Дней до истечения срока:/n  + ${repository.data}:${repository.time}")
        holder.documentImage.setImageURI(Uri.parse(repository.image))
        holder.documentImage.setOnClickListener {
            onPhotoClick(repository.image)

        }

        holder.openDocumentButton.setOnClickListener{
            onOpenFile(repository.filePath)
        }

        setJobProgressText(repository.executionState, holder.jobProgress, holder.done)

        holder.done.setOnClickListener {
            when (repository.executionState) {
                PROCESSING_STATE.IN_PROGRESS -> {
                    onDoneClick(repos[position])
                    repos.removeAt(position)
                }
                PROCESSING_STATE.DONE -> {
                    //do nothing
                }
                PROCESSING_STATE.NOT_SEEN -> {
                    repository.executionState = PROCESSING_STATE.IN_PROGRESS
                }
            }

            notifyDataSetChanged()
        }
    }

    private fun setJobProgressText(jobProgress: PROCESSING_STATE, textView: TextView, onDoneButton: Button) {
        when (jobProgress) {
            PROCESSING_STATE.IN_PROGRESS -> {
                onDoneButton.text = onDoneButton.context.getString(R.string.done)
                textView.text = "В процессе"
                textView.setTextColor(textView.context.resources.getColor(R.color.colorAccent))
            }
            PROCESSING_STATE.DONE -> {
                onDoneButton.isEnabled = false
                textView.text = "Выполнено"
                textView.setTextColor(textView.context.resources.getColor(R.color.green))
            }
            PROCESSING_STATE.NOT_SEEN -> {
                onDoneButton.text = onDoneButton.context.getString(R.string.take_in_progress)
                textView.text = "Не рассмотрено"
                textView.setTextColor(textView.context.resources.getColor(R.color.red))
            }
        }
    }

    private fun getName(start: Int, end: Int): String {
        return usersList[getRandomValue(start, end)]
    }

    private fun getRandomValue(start: Int, end: Int): Int {
        return randBetween(start, end)
    }

    private fun randBetween(start: Int, end: Int): Int {
        return start + Math.round(Math.random() * (end - start)).toInt()
    }

    fun addRepos(items: List<DocumentFile>) {
        repos.removeAll(repos)
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
        val documentType: TextView = itemView.findViewById(R.id.document_type)
        val author: TextView = itemView.findViewById(R.id.authorTextView)
        val documentName: TextView = itemView.findViewById(R.id.document_name)
        val done: Button = itemView.findViewById(R.id.done)
        val executionTime: TextView = itemView.findViewById(R.id.execution_time)
        val jobProgress: TextView = itemView.findViewById(R.id.jobProgress)
        val documentImage: ImageView = itemView.findViewById(R.id.document_image)
        val openDocumentButton: Button = itemView.findViewById(R.id.openDocumentButton)
    }

    enum class EXECTUTION_COLOR {
        DONE, IN_PROGRESS, NOT_TAKEN
    }
}