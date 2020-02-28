package top.bilibililike.player

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.WindowManager
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import top.bilibililike.mvp.base.BaseFragment
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.player.widget.live.subtitle.utils.ToastUtil
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.supportClass.MyPagerAdapter
import top.bilibililike.player.widget.antivirus.ui.AntiVirusFragment
import top.bilibililike.player.widget.bangumi.BangumiFragment
import top.bilibililike.player.widget.hotspot.HotSpotFragment
import top.bilibililike.player.widget.live.liveFragment.*
import top.bilibililike.player.widget.recommend.RecommendFragment
import top.bilibililike.player.widget.video.VideoFragment



class MainActivity : MVPActivity<LiveContract.ILivePresenter>() {
    override fun bindPresenter(): LivePresenter {
        return LivePresenter()
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var navController: NavController? = null

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


        val navController = findNavController(R.id.navHostFragment)
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val fragmentList = ArrayList<BaseFragment>()
        fragmentList.add(LiveFragment())
        fragmentList.add(RecommendFragment())
        fragmentList.add(HotSpotFragment())
        fragmentList.add(BangumiFragment())
        fragmentList.add(VideoFragment())
        fragmentList.add(AntiVirusFragment())

        viewPager.adapter = MyPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.offscreenPageLimit = 5

        //init tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[0].getTitle()))
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[1].getTitle()))
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[2].getTitle()))
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[3].getTitle()))
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[4].getTitle()))
        tabLayout.addTab(tabLayout.newTab().setText(fragmentList[5].getTitle()))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab) {
                Log.d("TabLayout reSelected", "${tab.text}")
                val view = tab.customView
                if (view is TextView) {
                    view.textSize = 20f
                    view.setTextColor(resources.getColor(R.color.colorPrimary))
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

                Log.d("TabLayout unSelected", "${tab.text}")
                val view = tab.customView
                if (view is TextView) {
                    view.textSize = 16f
                    view.setTextColor(resources.getColor(R.color.defaultTextGray))
                }


            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                Log.d("TabLayout Selected", "${tab.text}")
                viewPager.setCurrentItem(tab.position)
                val view = tab.customView
                if (!(view is TextView)) {
                    val textView = LayoutInflater.from(applicationContext).inflate(
                        R.layout.layout_textview,
                        null
                    ) as TextView;
                    textView.textSize = 20f
                    textView.text = tab.text
                    textView.setTextColor(resources.getColor(R.color.colorPrimary))
                    tab.setCustomView(textView)
                } else {
                    view.textSize = 20f;
                    view.setTextColor(resources.getColor(R.color.colorPrimary))
                }


            }

        })
        tabLayout.selectTab(tabLayout.getTabAt(1))

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position), true)
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

        requestReadAndWrite()

    }

    private fun requestReadAndWrite() {
        val permissions: Array<String> = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        try {
            var permission = ActivityCompat.checkSelfPermission(this, permissions[0])
            permission = permission and ActivityCompat.checkSelfPermission(this, permissions[1])
            if (permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 1)
            }

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


    }

    private fun requestDrawOverlays() {
        //判断权限
        fun commonRomPermissionCheck(): Boolean {
            var result = true
            try {
                val clazz = Settings::class.java
                val canDrawOverlays =
                    clazz.getDeclaredMethod("canDrawOverlays", Context::class.java)
                result = canDrawOverlays.invoke(null, this) as Boolean
            } catch (e: Exception) {
                Log.e("MainActivity", Log.getStackTraceString(e))
            }

            return result
        }

        //申请权限
        fun requestAlertWindowPermission() {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + packageName)
            startActivityForResult(intent, REQUEST_CODE)
        }
        if (!commonRomPermissionCheck()) {
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
            } else {
                toast("请通过存储权限")
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

}
