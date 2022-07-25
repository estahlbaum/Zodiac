package com.hfad.zodiac4

import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.Toast
//(binding)import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//(binding)import android.widget.CheckBox
//(binding)import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import com.hfad.zodiac4.databinding.TaskItemBinding


class TaskItemAdapter(val clickListener: (taskId: Long)->Unit)
    : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()) {
    //no longer needed since ListAdapters come with own backing list
    //var data = listOf<Task>()
    //set (value) {
    //    field = value
    //    notifyDataSetChanged()

    //tells adapter how many data items there are so it knows how many to display
    //override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    //view holder contains info about how a view in the item's layout should be displayed
    //(binding)class TaskItemViewHolder(val rootView: CardView)
    class TaskItemViewHolder(val binding: TaskItemBinding)
        :RecyclerView.ViewHolder(binding.root){
        //(binding)val taskName = rootView.findViewById<TextView>(R.id.task_name)
        //(binding)val taskDone = rootView.findViewById<CheckBox>(R.id.task_done)

        //inflates task_item
        companion object{
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                //(binding)val view =layoutInflater
                //(binding).inflate(R.layout.task_item, parent, false) as CardView
                val binding = TaskItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
        //how to display the task record in the view holder's layout
        fun bind(item:Task, clickListener: (taskId: Long) -> Unit){
            //(binding)taskName.text = item.taskName
            //(binding)taskDone.isChecked = item.taskDone
            binding.task = item

            binding.root.setOnClickListener{
                clickListener(item.taskId)}
        }
        //rootView.text = item.taskName
    }
}
