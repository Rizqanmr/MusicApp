package com.rizqanmr.musicapp.models

import com.google.gson.annotations.SerializedName

data class Track(
    @SerializedName("resultCount")
    val resultCount: Int = 0,
    @SerializedName("results")
    val results: List<TrackItem>?
)