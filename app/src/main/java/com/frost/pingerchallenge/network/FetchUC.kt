package com.frost.pingerchallenge.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.BufferedSource
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FetchUC @Inject constructor(private val repository: Repository) {

    suspend fun fetchStream(): Flow<List<String>>? {
        repository.fetchStreamSource().onSuccess {
            return flow { emit(getLinesOfInputFromSource(it.source())) }
        }
        return null
    }


    private fun getLinesOfInputFromSource(source: BufferedSource): List<String> {
        val list = arrayListOf<String>()
        try {
            while (!source.exhausted()) {
                val line = source.readUtf8Line()
                line?.let { list.add(it) }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return list.toList()
    }
}