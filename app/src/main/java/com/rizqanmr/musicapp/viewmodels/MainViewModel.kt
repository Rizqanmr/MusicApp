package com.rizqanmr.musicapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizqanmr.musicapp.models.TrackItem
import com.rizqanmr.musicapp.network.Result
import com.rizqanmr.musicapp.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel(), CoroutineScope {

    private val isLoading = MutableLiveData<Boolean>()
    private val listTrackLiveData: MutableLiveData<List<TrackItem>> = MutableLiveData()
    private val errorListTrackLiveData: MutableLiveData<String> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = viewModelScope.coroutineContext

    fun getIsLoading(): LiveData<Boolean> = isLoading

    private fun setIsLoading(isLoading: Boolean) {
        this.isLoading.value = isLoading
    }

    fun searchTrack() = viewModelScope.launch {
        setIsLoading(true)

        when (val result = repository.getTrackByTerm("komang")) {
            is Result.Success -> listTrackLiveData.value = result.data.results
            is Result.Error -> errorListTrackLiveData.value = result.error
        }

        setIsLoading(false)
    }

    fun listTrackLiveData(): MutableLiveData<List<TrackItem>> = listTrackLiveData

    fun errorListTrackLiveData(): LiveData<String> = errorListTrackLiveData
}