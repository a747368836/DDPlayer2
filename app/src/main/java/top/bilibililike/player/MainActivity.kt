package top.bilibililike.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import top.bilibililike.player.ui.live.subtitle.utils.ToastUtil
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.supportClass.MyPagerAdapter
import top.bilibililike.player.ui.antiVirus.AntiVirusFragment
import top.bilibililike.player.ui.followBangumi.FollowBangumiFragment
import top.bilibililike.player.ui.hotSpot.HotSpotFragment
import top.bilibililike.player.ui.live.liveFragment.*
import top.bilibililike.player.ui.recommend.RecommendFragment
import top.bilibililike.player.ui.video.VideoFragment


class MainActivity : MVPActivity<LiveContract.ILivePresenter>() {
    override fun bindPresenter(): LivePresenter {
        return LivePresenter()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navController:NavController? = null

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val REQUEST_CODE = 1
    }

    override fun getLayoutId(): Int {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        return R.layout.activity_main
    }



    override fun initView() {
        //init toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.navHostFragment)
        val tabLayout:TabLayout = findViewById(R.id.tabLayout)
        val viewPager:ViewPager = findViewById(R.id.viewPager)
        val fragmentList = ArrayList<Fragment>()
        fragmentList.add(LiveFragment())
        fragmentList.add(RecommendFragment())
        fragmentList.add(HotSpotFragment())
        fragmentList.add(FollowBangumiFragment())
        fragmentList.add(VideoFragment())
        fragmentList.add(AntiVirusFragment())

        viewPager.adapter = MyPagerAdapter(supportFragmentManager,fragmentList)

        //init tabLayout
        tabLayout.addTab(tabLayout.newTab().setText("直播"))
        tabLayout.addTab(tabLayout.newTab().setText("推荐"))
        tabLayout.addTab(tabLayout.newTab().setText("热门"))
        tabLayout.addTab(tabLayout.newTab().setText("追番"))
        tabLayout.addTab(tabLayout.newTab().setText("影视"))
        tabLayout.addTab(tabLayout.newTab().setText("抗击肺炎"))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab) {
                //todo refresh and play refresh animetion
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position),true)
            }

        })

        //init appBar's navitation and item
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        requestDrawOverlays()

    }

    private fun requestDrawOverlays(){
        //判断权限
        fun commonRomPermissionCheck(): Boolean {
            var result = true
            try {
                val clazz = Settings::class.java
                val canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context::class.java)
                result = canDrawOverlays.invoke(null, this) as Boolean
            } catch (e: Exception) {
                Log.e("MainActivity", Log.getStackTraceString(e))
            }

            return result
        }

        //申请权限
        fun requestAlertWindowPermission() {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:"+packageName)
            startActivityForResult(intent,REQUEST_CODE)
        }
        if (!commonRomPermissionCheck()){
            requestAlertWindowPermission()
        }

    }

    //处理回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                ToastUtil.show("请通过悬浮窗权限申请！")
                requestDrawOverlays()
            }
        }
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.navHostFragment)
        return navController!!.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}
