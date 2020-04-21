package com.aion.mobden.models

data class Word(
    val id :Int,
    val title:String,
    val description:String,
    val image:String) {}
















//class HttpTask : AsyncTask<String?, String?, String?>() {
//
//    private  val CONNECT_TIMEOUT = 15L
//    private  val READ_TIMEOUT = 15L
//    private  val WRITE_TIMEOUT = 15L
//    override fun doInBackground(vararg params: String?): String? {
//
//        return when(params[0]?.toLowerCase()){
//            "get" -> httpGet(params[1]!!,"")
//            "post" -> httpPost(params[1]!!,params[2]!!,"")
//            else -> ""
//        }
//
//    }
//
//
//    private fun httpGet(urlString: String, token: String): String? {
//        return try {
//            val client = OkHttpClient.Builder()
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .build()
//
//            val request = Request.Builder()
//                .url(URL(urlString))
//                //.header("Authorization", token)
//                .get()
//                .build()
//
//            val response = client.newCall(request).execute()
//            //println(response.body?.string())
//            response.body?.string()
//        }
//        catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//
//    private fun httpPost(urlString: String, jsonString: String, token: String): String? {
//        return try {
//            val client = OkHttpClient.Builder()
//                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
//                .build()
//
//            val body = jsonString.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
//
//            val request = Request.Builder()
//                .url(URL(urlString))
//                .header("Authorization", token)
//                .post(body)
//                .build()
//
//            val response = client.newCall(request).execute()
//            response.body?.string()
//        }
//        catch (e: IOException) {
//            e.printStackTrace()
//            null
//        }
//    }
//
//
//    fun objectToJson(obj: Any): String {
//        val gson = Gson()
//        return gson.toJson(obj)
////        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
////        return gsonPretty.toJson(obj)
//    }
//
//    @Throws(IOException::class)
//    inline fun <reified T:Any> jsonToObject(json: String?): T? {
//        return if (json == null) { null } else {
//            val gson = Gson()
//            gson.fromJson(json, T::class.java)
//        }
//    }
//
//
//}