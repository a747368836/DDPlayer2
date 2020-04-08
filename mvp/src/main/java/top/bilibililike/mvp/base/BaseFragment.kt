package top.bilibililike.mvp.base;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    var bundle:Bundle? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun getLayoutId(): Int

    abstract fun getTitle() : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.bundle = arguments;
        initBefore()
        initView()
        initListener()
        initData()
    }

    open fun initBefore() {}

    open fun initView() {}

    open fun initListener() {}

    open fun initData() {}
}