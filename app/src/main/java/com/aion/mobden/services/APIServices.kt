package com.aion.mobden.services

import com.aion.mobden.models.*
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface WordService {

    @GET("/MobdenAPI/Word/GetWord")
    fun getWord():Observable<Word>
}



interface ArticleService {

    @GET("/MobdenAPI/ManagmentArticles/GetArticles")
    fun getTopThreeArticles():Observable<ArrayList<Article>>


    @POST("/MobdenAPI/Article/GetArticleByStage")
    fun getArticlesByStage(@Body article:Article):Observable<ArrayList<Article>>


}



interface AboutSchoolService {

    @GET("/MobdenAPI/AboutSchool/GetAboutSchool")
    fun getAboutSchool():Observable<ArrayList<AboutSchool>>
}



interface HonorBoardService {

    @GET("/MobdenAPI/HonorBoard/GetManagmentHonorBoards")
    fun getManagmentHonorBoards():Observable<ArrayList<HonorBoard>>
}



interface NewsService {

    @POST("/MobdenAPI/News/GetThreeNewsByStage")
    fun getThreeNewsByStage(@Body news: News):Observable<ArrayList<News>>
}