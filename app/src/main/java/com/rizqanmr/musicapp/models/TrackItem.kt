package com.rizqanmr.musicapp.models

import com.google.gson.annotations.SerializedName

data class TrackItem(
    @SerializedName("artworkUrl100")
    val artworkUrlLarge: String = "",
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Int = 0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("previewUrl")
    val previewUrl: String = "",
    @SerializedName("artistId")
    val artistId: Int = 0,
    @SerializedName("trackName")
    val trackName: String = "",
    @SerializedName("collectionName")
    val collectionName: String = "",
    @SerializedName("artistViewUrl")
    val artistViewUrl: String = "",
    @SerializedName("discNumber")
    val discNumber: Int = 0,
    @SerializedName("trackCount")
    val trackCount: Int = 0,
    @SerializedName("artworkUrl30")
    val artworkUrlSmall: String = "",
    @SerializedName("wrapperType")
    val wrapperType: String = "",
    @SerializedName("currency")
    val currency: String = "",
    @SerializedName("collectionId")
    val collectionId: Int = 0,
    @SerializedName("isStreamable")
    val isStreamable: Boolean = false,
    @SerializedName("trackExplicitness")
    val trackExplicitness: String = "",
    @SerializedName("collectionViewUrl")
    val collectionViewUrl: String = "",
    @SerializedName("trackNumber")
    val trackNumber: Int = 0,
    @SerializedName("releaseDate")
    val releaseDate: String = "",
    @SerializedName("kind")
    val kind: String = "",
    @SerializedName("trackId")
    val trackId: Int = 0,
    @SerializedName("collectionPrice")
    val collectionPrice: Double = 0.0,
    @SerializedName("discCount")
    val discCount: Int = 0,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String = "",
    @SerializedName("trackPrice")
    val trackPrice: Double = 0.0,
    @SerializedName("collectionExplicitness")
    val collectionExplicitness: String = "",
    @SerializedName("trackViewUrl")
    val trackViewUrl: String = "",
    @SerializedName("artworkUrl60")
    val artworkUrlMedium: String = "",
    @SerializedName("trackCensoredName")
    val trackCensoredName: String = "",
    @SerializedName("artistName")
    val artistName: String = "",
    @SerializedName("collectionCensoredName")
    val collectionCensoredName: String = ""
)