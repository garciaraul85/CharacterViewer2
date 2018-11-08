package com.xfinity.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RelatedTopic {

    @SerializedName("Result")
    @Expose
    var result: String? = null
    @SerializedName("FirstURL")
    @Expose
    var firstURL: String? = null
    @SerializedName("Text")
    @Expose
    var text: String? = null
    @SerializedName("Icon")
    @Expose
    var icon: Icon? = null

    fun create(text: String?, url: String?) {
        this.text = text

        this.icon = Icon()
        this.icon!!.create(url)
    }

}