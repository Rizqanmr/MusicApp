package com.rizqanmr.musicapp.repository

import com.rizqanmr.musicapp.datasources.RemoteDataSource
import com.rizqanmr.musicapp.models.Track
import com.rizqanmr.musicapp.network.Result
import javax.inject.Inject

class AppRepository @Inject constructor(private val remoteDataSource: RemoteDataSource) {

    suspend fun getTrackByTerm(term: String) : Result<Track> {
        return remoteDataSource.getTrackByTerm(term)
    }
}