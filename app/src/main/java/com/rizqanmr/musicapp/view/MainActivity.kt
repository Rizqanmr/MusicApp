package com.rizqanmr.musicapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.adapter.MusicAdapter
import com.rizqanmr.musicapp.databinding.ActivityMainBinding
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.MusicItem
import com.rizqanmr.musicapp.models.listMusic
import com.rizqanmr.musicapp.utils.setFitImageUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.playerFragment.playerLayout.isVisible = false
        binding.rvMusic.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            musicAdapter = MusicAdapter(listMusic)
            adapter = musicAdapter
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
            addItemDecoration(itemDecoration)
        }

        musicAdapter.setMusicListener(object : MusicAdapter.MusicListener {
            override fun onItemClick(itemTrackBinding: ItemTrackBinding, item: MusicItem) {
                with(binding.playerFragment) {
                    trackName.text = item.trackName
                    artistName.text = item.artistName
                    trackImage.setFitImageUrl(item.trackImageUrl, R.drawable.ic_broken_image)
                    playerLayout.isVisible = true
                }
            }

        })
    }
}