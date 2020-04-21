package com.aion.mobden

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.aion.mobden.models.Word
import com.aion.mobden.services.ServiceBuilder
import com.aion.mobden.services.WordService
import com.bumptech.glide.Glide
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * A simple [Fragment] subclass.
 */
class MasterHomeFragment : Fragment() {

    private val hosting :String = "https://mobdenapi.azurewebsites.net"
    private val wordPath :String = "/assets/word/"
    private  lateinit var word_txt : TextView
    private lateinit var word_img : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        word_txt  = view.findViewById(R.id.word_description_txt)
        word_img = view.findViewById(R.id.word_img)
        loadWord()

    }


    private fun loadWord(){

        val compositeDisposable = CompositeDisposable()
        compositeDisposable.add(

            ServiceBuilder.buildService(WordService::class.java).getWord()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {response -> onWordServiceResponse(response)}
        )
    }

    private fun onWordServiceResponse(response: Word?) {
        val word = response
        if(word?.id!! > 0){
            word_txt.text = word.title
            val image_url = hosting + wordPath + word.image
            Glide.with(word_img)  //2
                .load(image_url) //3
                .centerCrop() //4
                .placeholder(R.drawable.company_logo) //5
                .error(R.drawable.company_logo) //6
                .fallback(R.drawable.company_logo) //7
                .into(word_img) //8
        }
    }


}


