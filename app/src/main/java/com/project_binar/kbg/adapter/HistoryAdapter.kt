package com.project_binar.kbg.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project_binar.kbg.databinding.ItemHistoryBinding
import com.project_binar.kbg.model.history.GetHistoryData

class HistoryAdapter: RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private var data= mutableListOf<GetHistoryData>()
    fun setData(history: List<GetHistoryData>){
        data.clear()
        data.addAll(history)
        notifyDataSetChanged()
    }
    inner class ViewHolder(var binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bindViewHolder(getHistoryData: GetHistoryData){
            val date = getHistoryData.createdAt.toString().take(10).trim()
            val time = getHistoryData.createdAt.toString().substring(11, 19).trim()
            val result = getHistoryData.message.toString().trim()
            val mode = getHistoryData.mode.toString().trim()
            binding.date.text=date
            binding.message.text=result
            binding.time.text=time
            binding.mode.text=mode
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemHistoryBinding= ItemHistoryBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewHolder(data[position])
    }

    override fun getItemCount(): Int = data.size

}