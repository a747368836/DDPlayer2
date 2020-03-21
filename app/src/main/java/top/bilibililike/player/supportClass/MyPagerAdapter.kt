package top.bilibililike.player.supportClass


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyPagerAdapter(manager:FragmentManager,fragmentList:List<Fragment>) : FragmentPagerAdapter(manager){
    val fragments = fragmentList


    override fun getItem(position: Int): Fragment {
        return fragments.get(position)
    }

    override fun getCount(): Int {
        return fragments.size
    }




}