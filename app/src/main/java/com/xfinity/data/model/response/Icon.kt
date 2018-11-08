package com.xfinity.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Icon {

    @SerializedName("Height")
    @Expose
    var height: String? = null
    @SerializedName("URL")
    @Expose
    var url: String? = null
    @SerializedName("Width")
    @Expose
    var width: String? = null

    fun create(url: String?) {
        this.url = url
    }

}