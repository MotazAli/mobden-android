package com.aion.mobden


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.net.URL
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation : BottomNavigationView
    private lateinit var masterFragment: MasterFragment
    private lateinit var primaryFragment: PrimaryFragment
    private lateinit var middleFragment: MiddleFragment
    private lateinit var highFragment: HighFragment
    private lateinit  var master_toolbar : Toolbar
    private  lateinit var menu_bar_btn : ImageButton
    private lateinit var drawer : DrawerLayout
    private  lateinit var nvg_slider_view : NavigationView
    private val states = arrayOf(intArrayOf(-android.R.attr.state_checked),intArrayOf(android.R.attr.state_checked))
    private val masterColors = intArrayOf(Color.BLACK, Color.RED)
    private val stageColors = intArrayOf(Color.BLACK,Color.BLUE)
    private val masterColorStateList = ColorStateList(states,masterColors)
    private val stageColorStateList = ColorStateList(states,stageColors)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        masterFragment = MasterFragment()
        primaryFragment = PrimaryFragment()
        middleFragment = MiddleFragment()
        highFragment = HighFragment()

        bottomNavigation = this.findViewById(R.id.btm_nav)
        menu_bar_btn = this.findViewById(R.id.sidebar_toolbar_logo_btn)
        master_toolbar = this.findViewById(R.id.include_master_toolbar)
        drawer  = findViewById(R.id.drawer_layout);
        nvg_slider_view = this.findViewById(R.id.sidebar_navigation)


        this.setSupportActionBar(master_toolbar)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        val url : String = "https://mobdenapi.azurewebsites.net/MobdenAPI/Word/GetWord"
        val result : String = HttpTask().execute("Get",url)?.get().toString()
        println(result )

        menu_bar_btn.setOnClickListener(View.OnClickListener { v ->
            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
                drawer.closeDrawer(Gravity.RIGHT);
            } else {
                drawer.openDrawer(Gravity.RIGHT);

            }
        })


        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            when (item.itemId){


                R.id.master_menu_item -> {
                    master_toolbar.background = ActivityCompat.getDrawable(this, R.drawable.header2)
                    bottomNavigation.itemIconTintList = masterColorStateList
                    bottomNavigation.itemTextColor = masterColorStateList
                    nvg_slider_view.setBackgroundColor( this.resources.getColor( R.color.masterItemColor))
                    nvg_slider_view.menu.setGroupVisible(R.id.master_group_items,true)
                    nvg_slider_view.menu.setGroupVisible(R.id.stage_group_items,false)
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frgmant,masterFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.primay_menu_item -> {
                    master_toolbar.background = ActivityCompat.getDrawable(this, R.drawable.header)
                    bottomNavigation.itemIconTintList = stageColorStateList
                    bottomNavigation.itemTextColor = stageColorStateList
                    nvg_slider_view.setBackgroundColor( this.resources.getColor( R.color.stageItemColor))
                    nvg_slider_view.menu.setGroupVisible(R.id.master_group_items,false)
                    nvg_slider_view.menu.setGroupVisible(R.id.stage_group_items,true)
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frgmant,primaryFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.middle_menu_item -> {
                    master_toolbar.background = ActivityCompat.getDrawable(this, R.drawable.header)
                    bottomNavigation.itemIconTintList = stageColorStateList
                    bottomNavigation.itemTextColor = stageColorStateList
                    nvg_slider_view.setBackgroundColor( this.resources.getColor( R.color.stageItemColor))
                    nvg_slider_view.menu.setGroupVisible(R.id.master_group_items,false)
                    nvg_slider_view.menu.setGroupVisible(R.id.stage_group_items,true)
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frgmant,middleFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

                R.id.high_menu_item -> {
                    master_toolbar.background = ActivityCompat.getDrawable(this, R.drawable.header)
                    bottomNavigation.itemIconTintList = stageColorStateList
                    bottomNavigation.itemTextColor = stageColorStateList
                    nvg_slider_view.setBackgroundColor( this.resources.getColor( R.color.stageItemColor))
                    nvg_slider_view.menu.setGroupVisible(R.id.master_group_items,false)
                    nvg_slider_view.menu.setGroupVisible(R.id.stage_group_items,true)
                    this.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_frgmant,highFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
            }

            true
        }
        bottomNavigation.setSelectedItemId(R.id.master_menu_item)









//
//
//        val toggle = object : ActionBarDrawerToggle(
//            this, drawer, master_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
//            override fun onDrawerClosed(drawerView: View) {
//                super.onDrawerClosed(drawerView)
//            }
//
//            override fun onDrawerOpened(drawerView: View) {
//                super.onDrawerOpened(drawerView)
//            }
//
//            override fun onDrawerSlide(
//                drawerView: View,
//                slideOffset: Float
//            ) {
//
//                if(drawerView!=null ){
//                    super.onDrawerSlide(drawerView, 0F);
//                }else{
//                    super.onDrawerSlide(drawerView, slideOffset);
//                }
//            }
//        }
//
//        drawer.addDrawerListener(toggle) //.setDrawerListener(toggle);
//        toggle.isDrawerSlideAnimationEnabled = false;
//        toggle.syncState();




//        master_toolbar.setNavigationOnClickListener( View.OnClickListener { v ->
//
//            if (drawer.isDrawerOpen(Gravity.RIGHT)) {
//                drawer.closeDrawer(Gravity.RIGHT);
//            } else {
//                drawer.openDrawer(Gravity.RIGHT);
//
//            }
//
//        })




    }





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

         @Throws(JsonProcessingException::class)
         fun objectToJson(obj: Any): String {
             return ObjectMapper().writeValueAsString(obj)
         }

         @Throws(IOException::class)
         inline fun <reified T:Any> jsonToObject(json: String?): T? {
             return if (json == null) { null } else {
                 ObjectMapper().readValue<T>(json, T::class.java)
             }
         }


     }





}
