package dev.arch3rtemp.pokemon.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.Shimmer.AlphaHighlightBuilder
import com.facebook.shimmer.ShimmerFrameLayout

class ShimmerHelper private constructor(builder: Builder) {

    class Builder(
        val frameLayout: ShimmerFrameLayout,
        val context: Context
    ) {

        val parent: LinearLayout
        var shimmer: Shimmer

        init {
            parent = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            shimmer = AlphaHighlightBuilder()
                .setDuration(1000)
                .setBaseAlpha(0.1f)
                .setHighlightAlpha(0.3f)
                .setDropoff(1f)
                .setTilt(0f)
                .build()
        }

        fun addAnimation(shimmer: Shimmer): Builder {
            this.shimmer = shimmer
            return this
        }

        fun addHorizontal(vararg views: View?): Builder {
            val horizontalParent = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
            }
            for (view in views) {
                horizontalParent.addView(view)
            }
            parent.addView(horizontalParent)
            return this
        }

        fun addHorizontal(@LayoutRes vararg resIds: Int): Builder {
            val horizontalParent = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
            }
            for (resId in resIds) {
                LayoutInflater.from(context).inflate(resId, horizontalParent, true)
            }
            parent.addView(horizontalParent)
            return this
        }

        fun addHorizontal(@LayoutRes resId: Int, count: Int): Builder {
            val horizontalParent = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                orientation = LinearLayout.HORIZONTAL
            }
            for (i in 0 until count) {
                LayoutInflater.from(context).inflate(resId, horizontalParent, true)
            }
            parent.addView(horizontalParent)
            return this
        }

        fun addView(view: View?): Builder {
            parent.addView(view)
            return this
        }

        fun addView(view: View?, count: Int): Builder {
            for (i in 0 until count) {
                parent.addView(view)
            }
            return this
        }

        fun addView(@LayoutRes resId: Int): Builder {
            LayoutInflater.from(context).inflate(resId, parent, true)
            return this
        }

        fun addView(@LayoutRes resId: Int, count: Int): Builder {
            for (i in 0 until count) {
                LayoutInflater.from(context).inflate(resId, parent, true)
            }
            return this
        }

        fun build(): ShimmerFrameLayout {
            frameLayout.setShimmer(shimmer)
            frameLayout.addView(parent)
            return frameLayout
        }
    }
}