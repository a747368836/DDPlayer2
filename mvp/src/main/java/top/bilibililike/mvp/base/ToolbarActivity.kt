package top.bilibililike.mvp.base;


import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import top.bilibililike.mvp.R

abstract class ToolbarActivity : BaseActivity() {

    var toolbarTitle: String = ""
        set(value) {
            field = value
            supportActionBar?.title = value
        }

    override fun initView() {
        super.initView()
        initToolbar()
    }

    /**
     *  Toolbar id must be toolbar
     */
    private fun initToolbar() {
        findViewById<Toolbar>(R.id.toolbar)?.let { toolbar ->
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            //将滑动菜单显示出来
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item!!)
    }
}