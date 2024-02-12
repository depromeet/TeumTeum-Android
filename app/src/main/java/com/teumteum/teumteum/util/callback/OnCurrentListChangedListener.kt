package com.teumteum.teumteum.util.callback

interface OnCurrentListChangedListener<T> {
    fun onCurrentListChanged(previousList: List<T>, currentList: List<T>)
}