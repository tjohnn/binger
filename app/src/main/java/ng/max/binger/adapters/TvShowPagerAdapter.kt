package ng.max.binger.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


internal class TvShowPagerAdapter(fm: FragmentManager,
                                 private val count: Int,
                                 private val titleFactory: (position: Int) -> String,
                                 private val fragmentFactory: (position: Int) -> Fragment) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = fragmentFactory(position)
    override fun getPageTitle(position: Int): CharSequence? = titleFactory(position)
    override fun getCount(): Int = count
}