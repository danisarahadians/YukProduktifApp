package com.codinginflow.YukProduktif.ui.tasks

import android.icu.text.Transliterator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.codinginflow.YukProduktif.data.Task
import com.codinginflow.YukProduktif.databinding.ItemTugasBinding

//datadata yang udah kita buat akan dihubungkan dengan layout di class taskadapter
class TaskAdapter : ListAdapter<Task, TaskAdapter.TaskViewHolder>(DiffCallBack()) {
    //membuat suatu tampilan dan mengembalikannya
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTugasBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }
    //menghubungkan data dengan view holder pada posisi yang ditentukan
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class TaskViewHolder(private val binding: ItemTugasBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.apply {
                checkboxCompleted.isChecked = task.completed
                textViewName.text = task.name
                textViewName.paint.isStrikeThruText = task.completed
                labelPriority.isVisible = task.important
            }
        }
    }
    //class callback yang digunakan untuk mengembalikan boolean
    class DiffCallBack : DiffUtil.ItemCallback<Task>() {
        //untuk mengatur are item the same, method ini berfungsi untuk menentukan apa yang menjadi indikator pembeda antara satu dengan yang
        //lainnya
        override fun areItemsTheSame(oldItem: Task, newItem: Task) =
            oldItem.id == newItem.id
        //method ini kitamenentukan indikator yang akan digunakan untuk menentukan apakah item kita berubah atau tidak.
        override fun areContentsTheSame(oldItem: Task, newItem: Task) =
            oldItem == newItem
    }
}