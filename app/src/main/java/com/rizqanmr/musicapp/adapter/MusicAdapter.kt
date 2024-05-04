package com.rizqanmr.musicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.MusicItem
import com.rizqanmr.musicapp.utils.setFitImageUrl

class MusicAdapter(private val musicList: List<MusicItem>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private lateinit var musicListener: MusicListener

    fun setMusicListener(musicListener: MusicListener) {
        this.musicListener = musicListener
    }

    class MusicViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MusicItem, listener: MusicListener) {
            (binding as? ItemTrackBinding)?.let { itemTrack ->
                itemTrack.item = item
                with(itemTrack) {
                    ivTrack.setFitImageUrl(item.trackImageUrl, R.drawable.ic_broken_image)
                    clTrack.setOnClickListener { listener.onItemClick(itemTrack, item) }
                }
            }
        }
    }

    interface MusicListener {
        fun onItemClick(itemTrackBinding: ItemTrackBinding, item: MusicItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun getItemCount(): Int = musicList.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item = musicList[position]
        holder.bindData(item, musicListener)
    }
}