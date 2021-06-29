package com.example.studentverificationapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.studentverificationapp.R
import com.example.studentverificationapp.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class SecurityOfficerDashBoardActivity : AppCompatActivity() {
    var viewPager: ViewPager? = null
    var pagerAdapter: ViewPagerAdapter? = null
    var Titles = arrayOf<CharSequence>("Scan Student", "Security Alerts")
    var Numboftabs = 2
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_security_officer_dash_board)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = null



        val fm:FragmentManager? = this.supportFragmentManager
        pagerAdapter = ViewPagerAdapter(fm, Titles, Numboftabs)

        // Assigning ViewPager View and setting the adapter

        // Assigning ViewPager View and setting the adapter
        viewPager = findViewById(R.id.admin_viewpager)
        viewPager?.setAdapter(pagerAdapter)

        val tabLayout = findViewById<TabLayout>(R.id.admin_tablayout)
        tabLayout.setupWithViewPager(viewPager)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {

        } else {
        }
        return super.onOptionsItemSelected(item)
    }
}