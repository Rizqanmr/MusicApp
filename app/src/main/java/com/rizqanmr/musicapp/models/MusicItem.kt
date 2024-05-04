package com.rizqanmr.musicapp.models

data class MusicItem(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val trackTimeMillis: String,
    val trackImageUrl: String
)

val listMusic = listOf(
    MusicItem(1, "Track One", "Artist One", "7:12", "https://is1-ssl.mzstatic.com/image/thumb/Features124/v4/45/ab/b7/45abb7a5-6a53-8d8f-91b0-03d1ef93111e/dj.zzffiuki.jpg/100x100bb.jpg"),
    MusicItem(2, "Track Two", "Artist Two", "3:02", "https://is1-ssl.mzstatic.com/image/thumb/Music115/v4/6d/73/b7/6d73b73e-dc59-cb79-8322-39842a57aa93/886447789421.jpg/100x100bb.jpg"),
    MusicItem(3, "Track Three", "Artist Three", "2:48", "https://is1-ssl.mzstatic.com/image/thumb/Music126/v4/60/95/6b/60956b3d-8358-b123-c219-cb9ee1c3f229/075597910070.jpg/100x100bb.jpg")
)
