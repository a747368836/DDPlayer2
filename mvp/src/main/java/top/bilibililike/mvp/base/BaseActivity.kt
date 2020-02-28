package top.bilibililike.mvp.base;

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 在界面未初始化之前调用的初始化窗口
        initWidows()

        if (initArgs(intent.extras)) {
            setContentView(getLayoutId())

            initBefore()
            initView()
            initListener()
            initData()
        } else {
            finish()
        }
    }

    open fun initArgs(bundle: Bundle?): Boolean = true

    open fun initWidows() {}

    abstract fun getLayoutId(): Int

    open fun initBefore() {}

    open fun initView() {}

    open fun initListener() {}

    open fun initData() {}
}