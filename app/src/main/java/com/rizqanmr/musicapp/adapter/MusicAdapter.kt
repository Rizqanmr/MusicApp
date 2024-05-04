package com.rizqanmr.musicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.MusicItem
import com.rizqanmr.musicapp.utils.setFitImageUrl

class MusicAdapter(private val musicList: List<MusicItem>) : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private lateinit var musicListener: MusicListener
    private var activeItemPos: Int? = null

    fun setMusicListener(musicListener: MusicListener) {
        this.musicListener = musicListener
    }

    class MusicViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: MusicItem, listener: MusicListener, isActive: Boolean) {
            (binding as? ItemTrackBinding)?.let { itemTrack ->
                itemTrack.item = item
                with(itemTrack) {
                    ivTrack.setFitImageUrl(item.trackImageUrl, R.drawable.ic_broken_image)
                    if (isActive) {
                        ivSound.isVisible = true
                        tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.dark_red))
                        listener.onItemClick(itemTrack, item)
                    } else {
                        ivSound.isVisible = false
                        tvTitle.setTextColor(ContextCompat.getColor(tvTitle.context, R.color.black))
                    }
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
        holder.bindData(item, musicListener, position == activeItemPos)
        holder.itemView.setOnClickListener { setActiveItem(position) }
    }

    private fun setActiveItem(position: Int) {
        val prevItemPos = activeItemPos
        activeItemPos = position
        prevItemPos?.let { notifyItemChanged(it) }
        notifyItemChanged(position)
    }
}