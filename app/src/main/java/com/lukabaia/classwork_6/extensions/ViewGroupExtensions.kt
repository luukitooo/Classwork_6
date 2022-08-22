package com.lukabaia.classwork_6.extensions

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.children

fun ViewGroup.areLinesEmpty(): Boolean {
    this.children.forEach {
        if (it is AppCompatEditText && it.text.isNullOrEmpty())
            return true
    }
    return false
}