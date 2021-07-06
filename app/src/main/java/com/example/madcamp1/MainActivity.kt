package com.example.madcamp1

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val fragCt by lazy { FragContact() }
    private val fragGm by lazy { FragGame() }
    private val fragGl by lazy { FragGallery() }

    val TAG: String = "로그"

    private val fragments: List<Fragment> = listOf(
        fragCt, fragGm, fragGl
    )

    private val pagerAdapter: MainPagerAdapter by lazy {
        MainPagerAdapter(this, fragments)
    }

    var navigation: BottomNavigationView ?= null
    var vpMain: ViewPager2 ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "MainActivity - onCreate() called")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation = findViewById<BottomNavigationView>(R.id.navigation)
        vpMain = findViewById<ViewPager2>(R.id.vp_main)

        initViewPager()
        initNavigationBar()


    }


    private fun initNavigationBar() {
        Log.d(TAG, "MainActivity - initNavigationBar() called")

        try {
            navigation = findViewById<BottomNavigationView>(R.id.navigation)
            vpMain = findViewById<ViewPager2>(R.id.vp_main)
        } catch (e: NullPointerException) {
            Log.e(TAG, "initNavigationBar: ", e)
        }

        navigation.run {
            this!!.setOnNavigationItemSelectedListener {
                val page = when(it.itemId) {
                    R.id.contact -> 0
                    R.id.game -> 1
                    R.id.gallary -> 2
                    else -> 0
                }
                if (page!= vpMain!!.currentItem){
                    vpMain!!.currentItem = page
                }
                true
            }
            selectedItemId = R.id.contact
        }
    }

    private fun initViewPager() {
        Log.d(TAG, "MainActivity - initViewPager() called")

        vpMain.run {
            this!!.adapter = pagerAdapter
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    val nav = when(position) {
                        0 -> R.id.contact
                        1 -> R.id.game
                        2 -> R.id.gallary
                        else -> R.id.contact
                    }
                    if (navigation!!.selectedItemId != nav) {
                        navigation!!.selectedItemId = nav
                    }
                }
            })
        }
    }
}






















