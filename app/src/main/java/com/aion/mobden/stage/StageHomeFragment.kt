package com.aion.mobden.stage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.aion.mobden.MainActivity
import com.aion.mobden.R
import com.aion.mobden.models.News
import com.aion.mobden.models.NewsViewModel
import com.aion.mobden.utility.CustomRecyclerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 */
class StageHomeFragment : Fragment() {

    private lateinit var bottomNavigation : BottomNavigationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stage_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadThreeNewsStage(view)
    }

    private fun loadThreeNewsStage(view:View){

        val stage = this.getStageOrMasterNavigationItem()
        val recyclerView : RecyclerView = view.findViewById(R.id.news_recycler_view)
        val newsModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsModel.getThreeNewsByStage(stage).observe(viewLifecycleOwner, Observer{
            var data : MutableLiveData<ArrayList<News>> = MutableLiveData(it)
            val newsSchoolAdapter: CustomRecyclerAdapter<News> = CustomRecyclerAdapter(
                this.activity as MainActivity,
                data
            )
            recyclerView.adapter = newsSchoolAdapter
        })
    }


    fun getStageOrMasterNavigationItem():Int{
        bottomNavigation = this.requireActivity().findViewById(R.id.btm_nav)

        return when(bottomNavigation.selectedItemId){
            R.id.primay_menu_item -> 1
            R.id.middle_menu_item -> 2
            R.id.high_menu_item -> 3
            else -> 0
        }

    }


}
