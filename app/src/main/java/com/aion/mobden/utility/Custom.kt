package com.aion.mobden.utility

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.aion.mobden.MainActivity
import com.aion.mobden.R
import com.aion.mobden.models.AboutSchool
import com.aion.mobden.models.Article
import com.aion.mobden.models.HonorBoard
import com.aion.mobden.models.News
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy



class MyPagerAdapter(fm: FragmentActivity, fragments: ArrayList<Fragment> ) : FragmentStateAdapter(fm) {

    private val  mFragments:ArrayList<Fragment> = fragments
    override fun getItemCount(): Int = mFragments.size
    override fun createFragment(position: Int): Fragment = mFragments[position]
}





class ViewPagerItemFragment : Fragment()  {

    // widgets
    private lateinit var mImage : ImageView
    private lateinit var  mTitle: TextView

    // vars
    private lateinit var _item:Any
    private val hosting :String = "https://mobdenapi.azurewebsites.net"
    private val articlePath: String = "/assets/articles/"

    companion object{
        fun <T: Parcelable> getInstance(item:T):ViewPagerItemFragment{
            val fragment = ViewPagerItemFragment()
            item.let{
                val bundle = Bundle()
                bundle.putParcelable("item", item)
                fragment.arguments = bundle
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.arguments?.let {
            _item = it.getParcelable("item")!!
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.card_item,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mImage = view.findViewById(R.id.card_image)
        mTitle = view.findViewById(R.id.card_title)
        init()

        view.setOnClickListener {
            Log.d("RecyclerView", "CLICK!")
        }


    }


    private fun init(){
        _item?.let {
            val image_url = hosting + articlePath + (it as Article).image
            Glide.with(mImage)  //2
                .load(image_url) //3
                //.centerCrop() //4
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.drawable.company_logo) //5
                .error(R.drawable.company_logo) //6
                .fallback(R.drawable.company_logo) //7
                .into(mImage) //8

            mTitle.text =it.title
        }
    }


}








class  CustomRecyclerAdapter<T>(val context: MainActivity, val items : LiveData<ArrayList<T>>) : RecyclerView.Adapter<ItemHolder>(){

    private val hosting :String = "https://mobdenapi.azurewebsites.net"
    private val articlePath: String = "/assets/articles/"
    private val aboutPath = "/assets/about/"
    private val honorBoardsPath = "/assets/honorBoards/"
    private val newsPath = "/assets/images/news/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {


        val inflatedView = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false)
        return ItemHolder(inflatedView)
    }

    override fun getItemCount(): Int = items.value!!.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {

        items.observe(context, Observer {

            it?.let {
                var title = ""
                var image = ""
                var image_path =""
                var color : Drawable? = null
                when (it.first()) {
                    is AboutSchool -> {
                        title = (it[position] as AboutSchool).tile
                        image = (it[position] as AboutSchool).image
                        image_path = aboutPath
                        color = ContextCompat.getDrawable(context, R.color.aboutCardColor)
                        holder.card_constraintLayout.background = color
                    }
                    is HonorBoard -> {
                        title = (it[position] as HonorBoard).description
                        image = (it[position] as HonorBoard).image
                        image_path = honorBoardsPath
                    }
                    is News ->{
                        title = (it[position] as News).title ?: ""
                        image = (it[position] as News).image ?: ""
                        image_path = newsPath
                        color = ContextCompat.getDrawable(context, R.color.stageCardColor)
                        holder.card_constraintLayout.background = color
                    }
                    is Article ->{
                        title = (it[position] as Article).title ?: ""
                        image = (it[position] as Article).image ?: ""
                        image_path = newsPath
                        color = ContextCompat.getDrawable(context, R.color.stageCardColor)
                        holder.card_constraintLayout.background = color
                    }
                    else -> ""
                }


                holder.card_title.text = title
                val image_url = hosting + image_path + image

                Glide.with(holder.card_img)  //2
                    .load(image_url) //3
                    //.centerCrop() //4
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(R.drawable.company_logo) //5
                    .error(R.drawable.company_logo) //6
                    .fallback(R.drawable.company_logo) //7
                    .into(holder.card_img) //8
            }
        } )


    }

}


class ItemHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    var card_img : ImageView = view.findViewById(R.id.card_image)
    var card_title : TextView = view.findViewById(R.id.card_title)
    var card_constraintLayout : ConstraintLayout = view.findViewById(R.id.card_item)
    //3
    init {
        view.setOnClickListener(this)
    }

    //4
    override fun onClick(v: View) {
        Log.d("RecyclerView", "CLICK!")
    }

    companion object {
        //5
        private val PHOTO_KEY = "PHOTO"
    }
}


