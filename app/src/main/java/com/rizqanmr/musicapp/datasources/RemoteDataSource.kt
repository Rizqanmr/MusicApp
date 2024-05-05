package com.rizqanmr.musicapp.datasources

import com.google.gson.Gson
import com.rizqanmr.musicapp.models.Track
import com.rizqanmr.musicapp.network.ApiService
import com.rizqanmr.musicapp.network.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    suspend fun getTrackByTerm(term: String) : Result<Track> {
        return withContext(coroutineContext) {
            try {
                val response = apiService.getTrackByTerm(term)
                Result.Success(response)
            } catch (e: Exception) {
                Result.Error("Error: $e")
            }
        }
    }
}