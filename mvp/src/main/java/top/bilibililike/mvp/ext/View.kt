package top.bilibililike.mvp.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView


// 关闭软键盘
fun View.hideKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

// 显示软键盘
fun View.showKeyboard() {
    val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

// 获取text内容
fun TextView.str(): String {
    return this.text.toString()
}


fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}
