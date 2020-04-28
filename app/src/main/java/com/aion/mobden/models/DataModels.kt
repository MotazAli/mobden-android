package com.aion.mobden.models

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aion.mobden.services.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.parcel.Parcelize


///////////// Word Model ////////////
data class Word(
    val id :Int,
    val title:String,
    val description:String,
    val image:String) {}


class WordRepository{
    private val compositeDisposable : CompositeDisposable = CompositeDisposable()
    private var  word :  MutableLiveData<Word>  = MutableLiveData()


    fun getWord(): LiveData<Word> {
        loadWord()
        return word
    }

    private fun loadWord(){
        compositeDisposable.add(
            ServiceBuilder.buildService( WordService::class.java).getWord()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {response -> onWordServiceResponse(response)}
        )
    }

    private fun onWordServiceResponse(response: Word?) {
        word.value = response
    }

    fun dispose() = compositeDisposable.dispose()

}

class WordViewModel : ViewModel() {

    private val wordRepository : WordRepository = WordRepository()

    fun getWord(): LiveData<Word> = wordRepository.getWord()

    override fun onCleared() {
        super.onCleared()
        wordRepository.dispose()
    }
}



///////////// Article Model ////////////
@Parcelize data class Article(
    @SerializedName("id")
    @Expose
    val id :Int? = null,
    @SerializedName("stage")
    @Expose
    val stage:Int? = null,
    @SerializedName("title")
    @Expose
    val title:String? = null,
    @SerializedName("description")
    @Expose
    val description:String? = null,
    @SerializedName("image")
    @Expose
    val image:String? = null,
    @SerializedName("creationDate")
    @Expose
    val creationDate:String? = null,
    @SerializedName("modifiedDate")
    @Expose
    val modifiedDate : String? = null) : Parcelable{}

class ArticleRepository{
    private val compositeDisposable: CompositeDisposable= CompositeDisposable()
    private var topThreeArticles: MutableLiveData<ArrayList<Article>> = MutableLiveData()
    private var articlesStage: MutableLiveData<ArrayList<Article>> = MutableLiveData()


    fun getTopThreeArticles(): LiveData<ArrayList<Article>> {
        loadTopThreeArticles()
        return topThreeArticles
    }



    private fun loadTopThreeArticles(){
        compositeDisposable.add(
            ServiceBuilder.buildService( ArticleService::class.java).getTopThreeArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter{ !it.isNullOrEmpty()}
                .subscribe {response -> onTopThreeArticlesResponse(response)}
        )
    }

    fun getArticlesByStage(article : Article): LiveData<ArrayList<Article>> {
        loadArticlesByStage(article)
        return articlesStage
    }

    private fun loadArticlesByStage(article : Article){
        compositeDisposable.add(
            ServiceBuilder.buildService( ArticleService::class.java).getArticlesByStage(article)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter{ !it.isNullOrEmpty()}
                .subscribe {response -> onArticlesByStageResponse(response)}
        )
    }

    private fun onTopThreeArticlesResponse(response: ArrayList<Article>?) {
        topThreeArticles.value = response
    }

    private fun onArticlesByStageResponse(response: ArrayList<Article>?) {
        articlesStage.value = response
    }

    fun dispose() = compositeDisposable.dispose()


}

class ArticleViewModel : ViewModel() {

    private val articleRepository: ArticleRepository = ArticleRepository()

    fun getTopThreeArticles(): LiveData<ArrayList<Article>> = articleRepository.getTopThreeArticles()

    fun getArticlesByStage(stage : Int = 0): LiveData<ArrayList<Article>> {
        val article = Article(stage=stage)
        return articleRepository.getArticlesByStage(article)
    }

    override fun onCleared() {
        super.onCleared()
        articleRepository.dispose()
    }
}



///////////// About School Model ////////////
 data class AboutSchool(val id :Int,
                              val stage:Int,
                              val tile:String,
                              val link:String,
                              val image:String,
                              val creationDate:String,
                              val modifiedDate : String) {}

class AboutSchoolRepository{

    private val compositeDisposable: CompositeDisposable= CompositeDisposable()
    private var allAboutSchool: MutableLiveData<ArrayList<AboutSchool>> = MutableLiveData()

    fun getAboutSchool(): LiveData<ArrayList<AboutSchool>> {
        loadAboutSchool()
        return allAboutSchool
    }

    private fun loadAboutSchool(){
        compositeDisposable.add(
            ServiceBuilder.buildService( AboutSchoolService::class.java).getAboutSchool()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter{ !it.isNullOrEmpty() }
                .subscribe ({response -> onResponse(response)}, {error -> onError(error)})
        )
    }

    private fun onError(error: Throwable?) {
        if (error != null)
            println(error)
    }

    private fun onResponse(response: ArrayList<AboutSchool>?) {
        allAboutSchool.value = response
    }

    fun dispose() = compositeDisposable.dispose()

}

class AboutSchoolViewModel : ViewModel() {

    private val aboutSchoolRepository: AboutSchoolRepository = AboutSchoolRepository()

    fun getAboutSchool(): LiveData<ArrayList<AboutSchool>> = aboutSchoolRepository.getAboutSchool()

    override fun onCleared() {
        super.onCleared()
        aboutSchoolRepository.dispose()
    }
}



///////////// Honor Board Model ////////////
data class HonorBoard(val id :Int,
                      val studentFullName:String,
                       val studentStage:Int,
                       val studentClass:Int,
                       val description:String,
                       val image:String,
                       val creationDate:String,
                       val modifiedDate : String) {}

class HonorBoardRepository{

    private val compositeDisposable: CompositeDisposable= CompositeDisposable()
    private var allHonorBoard: MutableLiveData<ArrayList<HonorBoard>> = MutableLiveData()

    fun getManagmentHonorBoards(): LiveData<ArrayList<HonorBoard>> {
        loadManagmentHonorBoards()
        return allHonorBoard
    }

    private fun loadManagmentHonorBoards(){
        compositeDisposable.add(
            ServiceBuilder.buildService( HonorBoardService::class.java).getManagmentHonorBoards()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter{ !it.isNullOrEmpty() && it.count() > 0 }
                .subscribe({response -> onResponse(response)}, {error -> onError(error)})
        )
    }

    private fun onError(error: Throwable?) {
        if (error != null)
            println(error)
    }

    private fun onResponse(response: ArrayList<HonorBoard>?) {
        allHonorBoard.value = response
    }

    fun dispose() = compositeDisposable.dispose()

}

class HonorBoardViewModel : ViewModel() {

    private val honorBoardRepository: HonorBoardRepository = HonorBoardRepository()

    fun getManagmentHonorBoards(): LiveData<ArrayList<HonorBoard>> = honorBoardRepository.getManagmentHonorBoards()

    override fun onCleared() {
        super.onCleared()
        honorBoardRepository.dispose()
    }
}




/////////////News Model ////////////
data class News(
    @SerializedName("id")
    @Expose
    var id :Int? = null,
    @SerializedName("stage")
    @Expose
    var stage:Int? = null,
    @SerializedName("title")
    @Expose
     var title:String? = null,
    @SerializedName("description")
    @Expose
    var description:String? = null,
    @SerializedName("image")
    @Expose
     var image:String? = null,
    @SerializedName("facebookUrl")
    @Expose
    var facebookUrl:String? = null,
    @SerializedName("creationDate")
    @Expose
    var creationDate:String? = null,
    @SerializedName("modifiedDate")
    @Expose
    var modifiedDate : String? = null
) {}

class NewsRepository{

    private val compositeDisposable: CompositeDisposable= CompositeDisposable()
    private var topThreeNews: MutableLiveData<ArrayList<News>> = MutableLiveData()

    fun getThreeNewsByStage(news : News): LiveData<ArrayList<News>> {
        loadThreeNewsByStage(news)
        return topThreeNews
    }

    private fun loadThreeNewsByStage(news : News){
        compositeDisposable.add(
            ServiceBuilder.buildService( NewsService::class.java).getThreeNewsByStage(news)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .filter{ !it.isNullOrEmpty()}
                .subscribe({response -> onResponse(response)}, {error -> onError(error)})
        )
    }

    private fun onError(error: Throwable?) {
        if (error != null)
            println(error)
    }

    private fun onResponse(response: ArrayList<News>?) {
        topThreeNews.value = response
    }

    fun dispose() = compositeDisposable.dispose()

}

class NewsViewModel : ViewModel() {

    private val newsRepository: NewsRepository = NewsRepository()

    fun getThreeNewsByStage(stage: Int = 0 ): LiveData<ArrayList<News>> {

        val news = News(stage = stage)
        return newsRepository.getThreeNewsByStage(news)
    }

    override fun onCleared() {
        super.onCleared()
        newsRepository.dispose()
    }
}


