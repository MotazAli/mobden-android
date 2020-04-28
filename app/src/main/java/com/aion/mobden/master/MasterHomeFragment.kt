package com.aion.mobden.master


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aion.mobden.MainActivity
import com.aion.mobden.R
import com.aion.mobden.models.*
import com.aion.mobden.utility.CustomRecyclerAdapter
import com.aion.mobden.utility.MyPagerAdapter
import com.aion.mobden.utility.ViewPagerItemFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class MasterHomeFragment : Fragment() {

    private val hosting :String = "https://mobdenapi.azurewebsites.net"
    private val wordPath :String = "/assets/word/"
    private  lateinit var word_txt : TextView
    private lateinit var word_img : ImageView
    private lateinit var wordModel : WordViewModel
    // widgets
    private lateinit var  mMyViewPager: ViewPager2
    private lateinit var  mTabLayout: TabLayout



    private var currentPage: Int = 0
    private var ArticlesCount : Int = 0
    private lateinit var timer: Timer

    private val  DELAY_MS :Long= 500 //delay in milliseconds before task is to be executed
    private val  PERIOD_MS :Long= 3000 // time in milliseconds between successive task executions.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_master_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadWord(view)
        loadArticles(view)
        loadAboutSchool(view)
        loadHonorBoard(view)

    }



    private fun loadWord(view:View){
        word_txt  = view.findViewById(R.id.word_description_txt)
        word_img = view.findViewById(R.id.word_img)
        wordModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordModel.getWord().observe(viewLifecycleOwner, Observer {

            it?.apply {
                if(id > 0){
                    word_txt.text = title
                    val image_url = hosting + wordPath + image
                    Glide.with(word_img)  //2
                        .load(image_url) //3
                        //.fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .placeholder(R.drawable.company_logo) //5
                        .error(R.drawable.company_logo) //6
                        .fallback(R.drawable.company_logo) //7
                        .into(word_img) //8
                }
            }


        })
    }


    private fun loadArticles(view:View){

        mTabLayout = view.findViewById(R.id.articles_tabs_view);
        mMyViewPager = view.findViewById(R.id.articles_view_pager);
        val articleModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        articleModel.getTopThreeArticles().observe(viewLifecycleOwner,Observer{
            ArticlesCount = it.size
            val fragments = ArrayList<Fragment>()
            for( item in it){
                val fragment = ViewPagerItemFragment.getInstance(item)
                fragments.add(fragment)
            }
            val pagerAdapter =  MyPagerAdapter(this.requireActivity() , fragments)
            mMyViewPager.adapter = pagerAdapter
            TabLayoutMediator(mTabLayout,mMyViewPager,{ tab, position -> return@TabLayoutMediator }).attach()



            var disposable  = Single.timer(4000, TimeUnit.MILLISECONDS)
                .repeat(4000) //to perform your task every 5 seconds
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (currentPage == ArticlesCount) {
                        currentPage = 0
                        Log.d("items", ArticlesCount.toString())
                    }
                    mMyViewPager.setCurrentItem(currentPage++, true)
                }
        })



//        var recyclerView : RecyclerView = view.findViewById(R.id.articles_recycler_view)
//        val articleModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
//        articleModel.getTopThreeArticles().observe(viewLifecycleOwner,Observer{
//            var data : MutableLiveData<ArrayList<Article>> = MutableLiveData(it)
//            articleAdapter = ArticleRecyclerAdapter(
//                this.activity as MainActivity,
//                data
//            )
//            recyclerView.adapter = articleAdapter
//        })
    }

    private fun loadAboutSchool(view:View){


        var recyclerView : RecyclerView = view.findViewById(R.id.about_school_recycler_view)
        val aboutSchoolModel = ViewModelProvider(this).get(AboutSchoolViewModel::class.java)
        aboutSchoolModel.getAboutSchool().observe(viewLifecycleOwner,Observer{
            var data : MutableLiveData<ArrayList<AboutSchool>> = MutableLiveData(it)
            val aboutSchoolAdapter: CustomRecyclerAdapter<AboutSchool> = CustomRecyclerAdapter(
                this.activity as MainActivity,
                data
            )
            recyclerView.adapter = aboutSchoolAdapter
            val recyclerViewEmpty : TextView = view.findViewById(R.id.about_school_recycler_view_empty)
            recyclerViewEmpty.visibility = View.GONE
        })
    }

    private fun loadHonorBoard(view:View){


        var recyclerView : RecyclerView = view.findViewById(R.id.honor_board_recycler_view)
        val honorBoardModel = ViewModelProvider(this).get(HonorBoardViewModel::class.java)
        honorBoardModel.getManagmentHonorBoards().observe(viewLifecycleOwner,Observer{

            it?.let {
                if (it.isNotEmpty())
                {
                    var data : MutableLiveData<ArrayList<HonorBoard>> = MutableLiveData(it)
                    val honorBoardAdapter: CustomRecyclerAdapter<HonorBoard> = CustomRecyclerAdapter(
                        this.activity as MainActivity,
                        data
                    )
                    recyclerView.adapter = honorBoardAdapter
                    val recyclerViewEmpty : TextView = view.findViewById(R.id.honor_board_recycler_view_empty)
                    recyclerViewEmpty.visibility = View.GONE
                }

            }

        })
    }


}






