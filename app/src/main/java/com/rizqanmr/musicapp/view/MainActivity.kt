package com.rizqanmr.musicapp.view

import android.content.Context
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.Toast
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
import kotlinx.coroutines.Runnable

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var musicAdapter: MusicAdapter
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var trackUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mediaPlayer = MediaPlayer()
        handler = Handler()
        viewModel.searchTrack("")
        setupToolbar()
        setupRecyclerview()
        selectedTrack()
        setupObservers()
    }

    private fun setupToolbar() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch()
                return@setOnEditorActionListener true
            }
            false
        }
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
                clearFocusEtSearch()
                with(binding.playerFragment) {
                    trackName.text = item.trackName
                    artistName.text = item.artistName
                    trackImage.setFitImageUrl(item.artworkUrlLarge, R.drawable.ic_broken_image)
                    playerLayout.isVisible = true
                    trackUrl = item.previewUrl
                    playTrack(trackUrl)
                    playPauseImageView.setImageResource(android.R.drawable.ic_media_pause)
                    playPauseLayout.setOnClickListener {
                        if (mediaPlayer.isPlaying) {
                            mediaPlayer.pause()
                            playPauseImageView.setImageResource(android.R.drawable.ic_media_play)
                        } else {
                            mediaPlayer.start()
                            playPauseImageView.setImageResource(android.R.drawable.ic_media_pause)
                        }
                    }
                    prevImageView.setOnClickListener {
                        Toast.makeText(this@MainActivity, "TODO: Prev Song", Toast.LENGTH_LONG).show()
                    }
                    nextImageView.setOnClickListener {
                        Toast.makeText(this@MainActivity, "TODO: Next Song", Toast.LENGTH_LONG).show()
                    }

                    seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                            if (fromUser) mediaPlayer.seekTo(progress)
                        }

                        override fun onStartTrackingTouch(p0: SeekBar?) {}

                        override fun onStopTrackingTouch(p0: SeekBar?) {}
                    })
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
                val onlySong: List<TrackItem> = list.filter { it.kind == "song" }
                layoutEmptyError.clEmptyError.isVisible = false
                rvMusic.isVisible = true
                musicAdapter.asyncListDiffer.submitList(onlySong)
            } else {
                rvMusic.isVisible = false
                layoutEmptyError.clEmptyError.isVisible = true
                layoutEmptyError.tvEmptyErrorTitle.text = "Oops, data not found. Please search with other keyword"
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

    private fun performSearch() {
        val query = binding.etSearch.text.toString()
        viewModel.searchTrack(query)
        clearFocusEtSearch()
    }

    private fun clearFocusEtSearch() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        binding.etSearch.clearFocus()
    }

    private fun playTrack(trackUrl: String) {
        if (mediaPlayer.isPlaying || !mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
        mediaPlayer.setDataSource(trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            binding.playerFragment.seekBar.max = mediaPlayer.duration
            mediaPlayer.start()
            updateSeekbar()
        }
    }

    private fun updateSeekbar() {
        runnable = Runnable {
            binding.playerFragment.seekBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacks(runnable)
    }
}