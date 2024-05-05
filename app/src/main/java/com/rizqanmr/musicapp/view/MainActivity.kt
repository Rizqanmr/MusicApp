package com.rizqanmr.musicapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rizqanmr.musicapp.R
import com.rizqanmr.musicapp.adapter.MusicAdapter
import com.rizqanmr.musicapp.databinding.ActivityMainBinding
import com.rizqanmr.musicapp.databinding.ItemTrackBinding
import com.rizqanmr.musicapp.models.TrackItem
import com.rizqanmr.musicapp.utils.setFitImageUrl
import com.rizqanmr.musicapp.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.searchTrack()
        setupRecyclerview()
        selectedTrack()
        setupObservers()
    }

    private fun setupRecyclerview() {
        musicAdapter = MusicAdapter()
        binding.playerFragment.playerLayout.isVisible = false
        binding.rvMusic.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = musicAdapter
            val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(getDrawable(R.drawable.divider)!!)
            addItemDecoration(itemDecoration)
        }
    }

    private fun selectedTrack() {
        musicAdapter.setMusicListener(object : MusicAdapter.MusicListener {
            override fun onItemClick(itemTrackBinding: ItemTrackBinding, item: TrackItem) {
                with(binding.playerFragment) {
                    trackName.text = item.trackName
                    artistName.text = item.artistName
                    trackImage.setFitImageUrl(item.artworkUrlLarge, R.drawable.ic_broken_image)
                    playerLayout.isVisible = true
                }
            }

        })
    }

    private fun setupObservers() {
        viewModel.getIsLoading().observe(this) {
            showLoading(it)
        }
        viewModel.listTrackLiveData().observe(this) {
            showListTrack(it)
        }
        viewModel.errorListTrackLiveData().observe(this) {
            handleError(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.layoutLoading.progressLoading.isVisible = isLoading
    }

    private fun showListTrack(list: List<TrackItem>) {
        with(binding) {
            if (list.isNotEmpty()) {
                rvMusic.isVisible = true
                musicAdapter.asyncListDiffer.submitList(list)
            } else {
                rvMusic.isVisible = false
                layoutEmptyError.clEmptyError.isVisible = true
                layoutEmptyError.tvEmptyErrorTitle.text = "Oops, data not found"
            }
        }
    }

    private fun handleError(errorMessage: String) {
        with(binding) {
            rvMusic.isVisible = false
            layoutEmptyError.clEmptyError.isVisible = true
            layoutEmptyError.tvEmptyErrorTitle.text = errorMessage
        }
    }
}