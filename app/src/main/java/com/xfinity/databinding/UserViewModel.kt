package com.xfinity.databinding

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Handler
import android.util.Log
import com.xfinity.BR
import com.xfinity.data.model.response.Icon
import com.xfinity.data.model.response.RelatedTopic
import java.util.*

class UserViewModel : BaseObservable() {

    @get:Bindable
    var userIds: MutableList<RelatedTopic> = mutableListOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.userIds)
        }

    /*@get:Bindable
    var changedPositions: Set<Int> = mutableSetOf()
        private set(value) {
            field = value
            notifyPropertyChanged(BR.changedPositions)
        }*/

    /*private val updateInterval = 1000L
    private val updateHandler = Handler()
    private val random = Random()

    private var updateRunnable: Runnable = object : Runnable {
        override fun run() {
            updateList()
            updateHandler.postDelayed(this, updateInterval)
        }
    }

    private fun updateList() {
        val pos1 = random.nextInt(10)
        var relatedTopic = RelatedTopic()
        var icon = Icon()
        icon.url = "https://duckduckgo.com/i/99b04638.png" //+ random.nextLong()
        relatedTopic.icon = icon
        relatedTopic.text = "Apu Nahasapeemapetilon - Apu Nahasapeemapetilon is a fictional character in the animated TV series The Simpsons. He is the Indian immigrant proprietor of the Kwik-E-Mart, a popular convenience store in Springfield, and is best known for his catchphrase, \"Thank you, come again.\""

        userIds.add(relatedTopic)

        changedPositions = setOf(pos1, pos1)
    }

    fun startUpdates() {
        initList()
        updateList()
        updateList()
        //updateHandler.postDelayed(updateRunnable, updateInterval)
    }

    private fun initList() {
        userIds = arrayListOf()
        Log.d("TAG", "ex")
    }

    fun stopUpdates() {
        updateHandler.removeCallbacks(updateRunnable)
    }*/
}