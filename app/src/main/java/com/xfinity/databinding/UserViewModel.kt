package com.xfinity.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.xfinity.BR
import com.xfinity.data.model.response.RelatedTopic

class UserViewModel : BaseObservable() {

    @get:Bindable
    var userIds: MutableList<RelatedTopic> = mutableListOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }


    fun startUpdates(list: List<RelatedTopic>) {
        userIds.addAll(list)
        notifyPropertyChanged(BR.userIds)
    }

    fun initList() {
        userIds = arrayListOf()

    }

}