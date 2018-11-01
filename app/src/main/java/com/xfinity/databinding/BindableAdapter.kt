package com.xfinity.databinding

interface BindableAdapter<T> {
    fun setData(items: List<T>)
}