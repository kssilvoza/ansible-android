package com.io.ansible.common.utility

import android.content.Context
import android.widget.ImageView
import com.io.ansible.common.picasso.CircleTransform
import com.squareup.picasso.Picasso

/**
 * Created by kimsilvozahome on 09/02/2018.
 */
class ImageUtility {
    companion object {
        fun loadCircleImage(context: Context?, url: String?, resId: Int, imageView: ImageView) {
            Picasso.with(context).invalidate(url)
            Picasso.with(context).load(url).placeholder(resId).transform(CircleTransform()).into(imageView)
        }
    }
}