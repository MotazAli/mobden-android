package com.aion.mobden


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


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

        init()


        this.setSupportActionBar(master_toolbar)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

//        val url : String = "https://mobdenapi.azurewebsites.net/MobdenAPI/Word/GetWord"
//        val result : String = HttpTask().execute("Get",url)?.get().toString()
//        println(result )

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


//        val master_navController :NavController = this.findNavController(R.id.master_nav_host_fragment)
//        nvg_slider_view.setupWithNavController(master_navController)






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

    override fun onSupportNavigateUp(): Boolean {
        var navController : NavController? = null
        if (bottomNavigation.selectedItemId == R.id.master_menu_item){
            navController = findNavController(R.id.master_nav_host_fragment)
        }
        return navController?.navigateUp() ?: super.onSupportNavigateUp()
    }


    private fun init() {
        masterFragment = MasterFragment()
        primaryFragment = PrimaryFragment()
        middleFragment = MiddleFragment()
        highFragment = HighFragment()
        bottomNavigation = this.findViewById(R.id.btm_nav)
        menu_bar_btn = this.findViewById(R.id.sidebar_toolbar_logo_btn)
        master_toolbar = this.findViewById(R.id.include_master_toolbar)
        drawer  = findViewById(R.id.drawer_layout);
        nvg_slider_view = this.findViewById(R.id.sidebar_navigation)
    }



}
