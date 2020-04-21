package com.aion.mobden

import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

        val target_api : String = hosting + "/MobdenAPI/Word/GetWord"
        val result : String = HttpTask().execute("Get",target_api)?.get().toString()


        var word = HttpTask().jsonToObject<Word>(result)
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


data class Word(val id :Int,
           val title:String,
           val description:String,
           val image:String) {}


class HttpTask : AsyncTask<String?, String?, String?>() {

    private  val CONNECT_TIMEOUT = 15L
    private  val READ_TIMEOUT = 15L
    private  val WRITE_TIMEOUT = 15L
    override fun doInBackground(vararg params: String?): String? {

        return when(params[0]?.toLowerCase()){
            "get" -> httpGet(params[1]!!,"")
            "post" -> httpPost(params[1]!!,params[2]!!,"")
            else -> ""
        }

    }


    private fun httpGet(urlString: String, token: String): String? {
        return try {
            val client = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()

            val request = Request.Builder()
                .url(URL(urlString))
                //.header("Authorization", token)
                .get()
                .build()

            val response = client.newCall(request).execute()
            //println(response.body?.string())
            response.body?.string()
        }
        catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    private fun httpPost(urlString: String, jsonString: String, token: String): String? {
        return try {
            val client = OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .build()

            val body = jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

            val request = Request.Builder()
                .url(URL(urlString))
                .header("Authorization", token)
                .post(body)
                .build()

            val response = client.newCall(request).execute()
            response.body?.string()
        }
        catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    fun objectToJson(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj)
//        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
//        return gsonPretty.toJson(obj)
    }

    @Throws(IOException::class)
    inline fun <reified T:Any> jsonToObject(json: String?): T? {
        return if (json == null) { null } else {
            val gson = Gson()
            gson.fromJson(json, T::class.java)
        }
    }


}
