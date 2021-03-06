package com.example.studentverificationapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.studentverificationapp.fragments.PublicChatFragment
import com.example.studentverificationapp.fragments.ScanStudentFragment


class ViewPagerAdapter(
    fm: androidx.fragment.app.FragmentManager?,
        // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    private val Titles: Array<CharSequence>,
        // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    private var NumbOfTabs: Int) : FragmentStatePagerAdapter(fm!!) {
    //This method return the fragment for the every position in the View Pager
    override fun getItem(position: Int): Fragment {
        // if the position is 0 we are returning the First tab
        return if (position == 0)
        {
            ScanStudentFragment()

        } else {
            PublicChatFragment()

        }
    }

    // This method return the titles for the Tabs in the Tab Strip
    override fun getPageTitle(position: Int): CharSequence? {
        return Titles[position]
    }

    override fun getCount(): Int {
        return NumbOfTabs
    }

    // Build a Constructor and assign the passed Values to appropriate values in the class
    init {
        NumbOfTabs = NumbOfTabs
    }
}