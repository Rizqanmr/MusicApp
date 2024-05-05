package com.rizqanmr.musicapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.TrackItem
import com.rizqanmr.musicapp.utils.setFitImageUrl

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.MusicViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<TrackItem>() {
        override fun areItemsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TrackItem, newItem: TrackItem): Boolean {
            return oldItem.trackId == newItem.trackId
        }

    }

    val asyncListDiffer = AsyncListDiffer(this, diffUtil)
    private lateinit var musicListener: MusicListener
    private var activeItemPos: Int? = null

    fun setMusicListener(musicListener: MusicListener) {
        this.musicListener = musicListener
    }

    class MusicViewHolder(private val binding: ItemTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(item: TrackItem, listener: MusicListener, isActive: Boolean) {
            (binding as? ItemTrackBinding)?.let { itemTrack ->
                itemTrack.item = item
                with(itemTrack) {
                    ivTrack.setFitImageUrl(item.artworkUrlLarge, R.drawable.ic_broken_image)
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
        fun onItemClick(itemTrackBinding: ItemTrackBinding, item: TrackItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ItemTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicViewHolder(binding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val item = asyncListDiffer.currentList[position]
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