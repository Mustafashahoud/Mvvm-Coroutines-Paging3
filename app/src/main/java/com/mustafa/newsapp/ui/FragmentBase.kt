package com.mustafa.newsapp.ui

import androidx.databinding.ViewDataBinding

abstract class FragmentBase<T : ViewDataBinding> {

    abstract fun getBinding(): Int
}