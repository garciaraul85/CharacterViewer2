package com.xfinity.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SrcOptions {

    @SerializedName("skip_abstract_paren")
    @Expose
    var skipAbstractParen: Int? = null
    @SerializedName("is_fanon")
    @Expose
    var isFanon: Int? = null
    @SerializedName("skip_abstract")
    @Expose
    var skipAbstract: Int? = null
    @SerializedName("is_wikipedia")
    @Expose
    var isWikipedia: Int? = null
    @SerializedName("skip_icon")
    @Expose
    var skipIcon: Int? = null
    @SerializedName("skip_image_name")
    @Expose
    var skipImageName: Int? = null
    @SerializedName("skip_qr")
    @Expose
    var skipQr: String? = null
    @SerializedName("skip_end")
    @Expose
    var skipEnd: String? = null
    @SerializedName("language")
    @Expose
    var language: String? = null
    @SerializedName("source_skip")
    @Expose
    var sourceSkip: String? = null
    @SerializedName("src_info")
    @Expose
    var srcInfo: String? = null
    @SerializedName("min_abstract_length")
    @Expose
    var minAbstractLength: String? = null
    @SerializedName("directory")
    @Expose
    var directory: String? = null
    @SerializedName("is_mediawiki")
    @Expose
    var isMediawiki: Int? = null

}