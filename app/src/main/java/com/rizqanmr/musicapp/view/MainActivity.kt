package com.rizqanmr.musicapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.adapter.MusicAdapter
import com.rizqanmr.musicapp.databinding.ActivityMainBinding
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.MusicItem
import com.rizqanmr.musicapp.models.listMusic
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvMusic.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            musicAdapter = MusicAdapter(listMusic)
            adapter = musicAdapter
        }

        musicAdapter.setMusicListener(object : MusicAdapter.MusicListener {
            override fun onItemClick(itemTrackBinding: ItemTrackBinding, item: MusicItem) {
                print("item ${item.trackName} clicked")
            }

        })
    }
}