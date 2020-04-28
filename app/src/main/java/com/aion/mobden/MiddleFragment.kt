package com.aion.mobden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView

/**
 * A simple [Fragment] subclass.
 */
class MiddleFragment : Fragment() {

    private  lateinit var nvg_slider_view : NavigationView
    private lateinit  var master_toolbar : Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_middle, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nvg_slider_view = this.requireActivity().findViewById(R.id.sidebar_navigation)
        master_toolbar = this.requireActivity().findViewById(R.id.include_master_toolbar)
        val master_navController : NavController =  Navigation.findNavController(this.requireActivity(),R.id.stage_nav_host_fragment)
        nvg_slider_view.setupWithNavController(master_navController)
        NavigationUI.setupActionBarWithNavController(this.activity as AppCompatActivity, master_navController)

    }

}
