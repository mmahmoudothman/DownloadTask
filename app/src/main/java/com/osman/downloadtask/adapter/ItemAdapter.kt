package com.osman.downloadtask.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.osman.downloadtask.R
import com.osman.downloadtask.data.VideoModel
import com.osman.downloadtask.databinding.ItemFilesBinding

class ItemAdapter(
    private val itemsList: ArrayList<VideoModel>,
    private val videoSelectable: VideoSelectable
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_files,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoModel: VideoModel = itemsList[position]
        holder.binding(videoModel)
        val item = itemsList[position]
        when {
            item.startDownload -> {
                if (item.totalFileSize != -1L) {
                    val progressValue =
                        ((item.currentDownload.toDouble() / item.totalFileSize.toDouble()) * 100).toInt()
                    holder.binding.progressBar.progress = progressValue
                }
                holder.binding.txtProgressPercent.text = "${
                    String.format("%.2f", (item.currentDownload.toDouble() / (1048576)))}MB /${
                    if (item.totalFileSize == -1L) 0 else String.format("%.2f", (item.totalFileSize.toDouble() / (1048576)))}MB"
            }
        }
        holder.binding.root.setOnClickListener {
            videoSelectable.videoSelected(
                videoModel, position
            )
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    class ViewHolder(val binding: ItemFilesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(videoModel: VideoModel) {
            binding.videoModel = videoModel
            binding.executePendingBindings()
        }
    }

    fun updateItem(videoModel: VideoModel, index: Int) {
        itemsList[index] = videoModel
        notifyItemChanged(index)
    }

    interface VideoSelectable {
        fun videoSelected(videoModel: VideoModel, videoIndex: Int)
    }
}