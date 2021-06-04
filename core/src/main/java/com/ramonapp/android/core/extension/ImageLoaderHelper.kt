package com.ramonapp.android.core.extension

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.*
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.lang.StringBuilder


@GlideModule
class MyGlideModule : AppGlideModule()

object ImageLoaderHelper {

    private const val CURVE_SIZE = 4

    fun load(context: Context?, url: String?, callback: ((drawable: Drawable?) -> Unit)? = null) {
        context?.let {
            GlideApp.with(context).load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        callback?.invoke(resource)
                        return true
                    }

                })
                .into(object : CustomTarget<Drawable>() {
                    override fun onResourceReady(
                        resource: Drawable,
                        transition: Transition<in Drawable>?
                    ) {
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
        }
    }

    fun load(
        context: Context?,
        url: String?,
        imageView: ImageView,
        isCurved: Boolean = false,
        isCircle: Boolean = false,
        placeholder: Int? = null
    ) {
        if (url == null) {
            imageView.setImageDrawable(null)
            return
        }
        val path =  StringBuilder(url)
        context?.let {
            val glideReq = GlideApp.with(context)
                .load(path.toString())
                .transition(withCrossFade())

            if (isCurved) glideReq.apply(getOptionCurve())
            if (isCircle) glideReq.apply(RequestOptions.circleCropTransform())
            placeholder?.let { glideReq.apply(RequestOptions.placeholderOf(it)) }

            glideReq.into(imageView)
        }
    }


    private fun getOptionCurve() =
        RequestOptions().transform(RoundedCorners(CURVE_SIZE.toPx()))


}