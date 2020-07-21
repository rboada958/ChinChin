package com.chinchinapp.chinchin.utils

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.chinchinapp.chinchin.R
import com.chinchinapp.chinchin.app.ChinChinApp

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null)
        text?.observe(parentActivity, Observer { value -> view.text = value ?: "" })
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View, visibility: MutableLiveData<Int>) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null)
        visibility.observe(parentActivity, Observer { value ->
            view.visibility = value ?: View.GONE
        })
}

@BindingAdapter("mutableTextWatcher")
fun setMutableTextWatcher(edittext: EditText, textWatcher: TextWatcher) {
    val parentActivity: AppCompatActivity? = edittext.getParentActivity()
    if (parentActivity != null && edittext != null)
        edittext.addTextChangedListener(textWatcher)
}

@BindingAdapter("mutableLoadImage")
fun setMutableLoadImage(view: ImageView, url: Drawable?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null && url != null)
        Glide.with(view.context)
            .load(url)
            .override(300, 300)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(view)
}
@BindingAdapter("mutableLoadImageCrypto")
fun setMutableLoadImageCrypto(view: ImageView, url: String?) {
    view.setColorFilter(ContextCompat.getColor(ChinChinApp.appContext, R.color.colorWhite), android.graphics.PorterDuff.Mode.SRC_IN)
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && view != null && url != null)
        Glide.with(view.context)
            .load(url)
            .override(300, 300)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .centerCrop()
            .into(view)

}