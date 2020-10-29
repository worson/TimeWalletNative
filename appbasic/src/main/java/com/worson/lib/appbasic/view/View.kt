package com.worson.lib.appbasic.view

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.TouchDelegate
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.doOnLayout
import com.worson.lib.appbasic.application.GlobalContext


inline fun View.setOnSingleClickListener(
    delayMillis: Long = 800,
    crossinline onClick: (v: View) -> Unit
) {
    this.setOnClickListener {
        this.isClickable = false
        onClick(this)
        this.postDelayed({
            this.isClickable = true
        }, delayMillis)
    }
}

fun View.setDelegate(top: Int, bottom: Int, right: Int, left: Int) {
    doOnLayout {
        val bounds = Rect()
        it.isEnabled = true
        it.getHitRect(bounds)
        bounds.top -= top
        bounds.bottom += bottom
        bounds.left -= left
        bounds.right += right
        (it.parent as View).touchDelegate = TouchDelegate(bounds, it)
    }
}

fun View.onVisibilityChanged(block: (visibility: Int) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener{
        val newVis: Int = visibility
        if (tag as? Int != newVis) {
            tag = visibility
            //visibility has changed
            block(visibility)
        }
    }
}

fun @receiver:DrawableRes Int.drawable(): Drawable? = ContextCompat.getDrawable(GlobalContext.instance, this)
fun @receiver:StringRes Int.string(): String = GlobalContext.instance.getString(this)
fun @receiver:ColorRes Int.color(): Int = ContextCompat.getColor(GlobalContext.instance, this)

fun Int.resName(): String = GlobalContext.instance.resources.getResourceName(this)

fun View.visible(){
    this.visibility=View.VISIBLE
}

fun View.invisible(){
    this.visibility=View.INVISIBLE
}

fun View.gone(){
    this.visibility=View.GONE
}
