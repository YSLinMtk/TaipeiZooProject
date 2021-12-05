package com.ann.taipeizoo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadUrl(picUrl: String?) {
    Glide.with(this.context)
        .load(picUrl)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .skipMemoryCache(true)
        .override(320, 320)
        .centerCrop()
        .apply(RequestOptions.bitmapTransform(CircleCrop()).override(320, 320))
        .placeholder(R.drawable.ic_zoo)
        .into(this)
}

fun openBrowser(mContext: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    mContext.startActivity(intent)
}
