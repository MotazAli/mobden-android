package com.aion.mobden.services

import com.aion.mobden.models.Word
import io.reactivex.Observable
import retrofit2.http.GET

interface WordService {

    @GET("/MobdenAPI/Word/GetWord")
    fun getWord():Observable<Word>
}