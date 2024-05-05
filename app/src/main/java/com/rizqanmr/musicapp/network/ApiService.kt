package com.rizqanmr.musicapp.network

import com.rizqanmr.musicapp.models.Track
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getTrackByTerm(@Query("term") term: String) : Track
}