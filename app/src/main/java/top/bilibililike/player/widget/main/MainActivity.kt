package top.bilibililike.player.widget.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.Settings
import android.system.OsConstants.IPPROTO_TCP
import android.util.Log

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.navigation.ui.AppBarConfiguration
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import top.bilibililike.mvp.base.BaseFragment
import top.bilibililike.mvp.constant.Const
import top.bilibililike.mvp.ext.Toasts.toast
import top.bilibililike.mvp.mvp.MVPActivity
import top.bilibililike.player.R
import top.bilibililike.player.common.MyApp
import top.bilibililike.player.common.bean.userInfo.Data
import top.bilibililike.player.support.MyPagerAdapter
import top.bilibililike.player.widget.antivirus.ui.AntiVirusFragment
import top.bilibililike.player.widget.bangumi.BangumiFragment
import top.bilibililike.player.widget.hotspot.HotSpotFragment
import top.bilibililike.player.widget.live.liveFragment.*
import top.bilibililike.player.widget.live.subtitle.utils.ToastUtil
import top.bilibililike.player.widget.login.LoginActivity
import top.bilibililike.player.widget.recommend.RecommendFragment
import top.bilibililike.player.widget.search.SearchActivity
import top.bilibililike.player.widget.video.VideoFragment
import java.net.InetSocketAddress


class MainActivity : MVPActivity<MainContract.Presenter>(), MainContract.View {
    @SuppressLint("SetTextI18n")
    override fun showUserInfo(dataBean: Data?) {
        if (dataBean != null) {
            val avatarView = navView.getHeaderView(0).findViewById<ImageView>(R.id.ivAvatar)
            Glide.with(this).load(dataBean.face).into(avatarView)
            Glide.with(this).load(dataBean.face).into(imv_avatar)
            val nickname = navView.getHeaderView(0).findViewById<TextView>(R.id.tv_nickname)
            nickname.setText(dataBean.name)
            val coinTextView = navView.getHeaderView(0).findViewById<TextView>(R.id.tv_coins)
            coinTextView.setText("硬币：${dataBean.coins}\n签名：${dataBean.sign}")
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }
    }


    private lateinit var appBarConfiguration: AppBarConfiguration
    //private var navController: NavController? = null

    override fun bindPresenter(): MainContract.Presenter = MainPresenter(this)

    companion object {
        private val TAG = MainActivity::class.java.simpleName
        private val REQUEST_CODE = 1
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        //init toolbar
        /*val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)*/


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
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_tools,
                R.id.nav_share,
                R.id.nav_send
            ), drawerLayout
        )

        val intent = Intent(applicationContext, LoginActivity::class.java)
        val avatarView = navView.getHeaderView(0).findViewById<ImageView>(R.id.ivAvatar)
        avatarView?.setOnClickListener {
            if (!MyApp.hasLogin) {
                startActivity(intent)
            } else {
                ToastUtil.show("暂未开放（没做完）")
            }
        }
        val smallTvImgView = navView.getHeaderView(0).findViewById<ImageView>(R.id.small_tv_cry)
        if (!MyApp.hasLogin) {
            smallTvImgView.setImageDrawable(resources.getDrawable(R.mipmap.bili_drawerbg_not_logined))
        } else {
            smallTvImgView.setImageDrawable(resources.getDrawable(R.mipmap.bili_drawerbg_logined))
            smallTvImgView.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        smallTvImgView.setImageDrawable(resources.getDrawable(R.mipmap.bili_drawerbg_not_logined))
                    } else {
                        smallTvImgView.setImageDrawable(resources.getDrawable(R.mipmap.bili_drawerbg_logined))
                    }
                    return false
                }

            })
        }

        imv_search.setOnClickListener {
            startActivity(
                Intent(
                    applicationContext,
                    SearchActivity::class.java
                )
            )
        }

        requestDrawOverlays()

        requestReadAndWrite()

    }

    override fun initData() {
        presenter.loadUserInfo()
    }

    private fun checkReadAndWritePermission(): Boolean {
        val permissions: Array<String> = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        var permission = ActivityCompat.checkSelfPermission(this, permissions[0])
        permission = permission and ActivityCompat.checkSelfPermission(this, permissions[1])
        return permission == PackageManager.PERMISSION_GRANTED
    }

    private fun requestReadAndWrite() {
        val permissions: Array<String> = arrayOf(
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"
        )
        try {
            if (!checkReadAndWritePermission()) {
                ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
            }
        } catch (e: Exception) {
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
            startActivityForResult(
                intent,
                REQUEST_CODE
            )
        }
        if (!commonRomPermissionCheck()) {
            requestAlertWindowPermission()
        }


    }

    //处理权限申请回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                toast("请通过悬浮窗权限申请！")
                requestDrawOverlays()
            }
            if (!checkReadAndWritePermission()) {
                toast("请通过读写磁盘权限申请！")
                requestReadAndWrite()
            }


        }
    }


    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        val str = intent?.getStringExtra(Const.INTENT_EVENT)
        if (str != null && str == Const.LOGIN_SUCCESS) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            presenter.loadUserInfo()
        }
    }
}
