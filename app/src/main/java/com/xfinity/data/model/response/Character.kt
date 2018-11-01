package com.xfinity.data.model.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Character {

    @SerializedName("Definition")
    @Expose
    var definition: String? = null
    @SerializedName("DefinitionSource")
    @Expose
    var definitionSource: String? = null
    @SerializedName("ImageIsLogo")
    @Expose
    var imageIsLogo: Int? = null
    @SerializedName("Heading")
    @Expose
    var heading: String? = null
    @SerializedName("Infobox")
    @Expose
    var infobox: String? = null
    @SerializedName("AbstractSource")
    @Expose
    var abstractSource: String? = null
    @SerializedName("AnswerType")
    @Expose
    var answerType: String? = null
    @SerializedName("Entity")
    @Expose
    var entity: String? = null
    @SerializedName("Redirect")
    @Expose
    var redirect: String? = null
    @SerializedName("Abstract")
    @Expose
    var abstract: String? = null
    @SerializedName("ImageHeight")
    @Expose
    var imageHeight: Int? = null
    @SerializedName("AbstractText")
    @Expose
    var abstractText: String? = null
    @SerializedName("Type")
    @Expose
    var type: String? = null
    @SerializedName("Results")
    @Expose
    var results: List<Any>? = null
    @SerializedName("Image")
    @Expose
    var image: String? = null
    @SerializedName("DefinitionURL")
    @Expose
    var definitionURL: String? = null
    @SerializedName("RelatedTopics")
    @Expose
    var relatedTopics: List<RelatedTopic>? = null
    @SerializedName("Answer")
    @Expose
    var answer: String? = null
    @SerializedName("meta")
    @Expose
    var meta: Meta? = null
    @SerializedName("AbstractURL")
    @Expose
    var abstractURL: String? = null
    @SerializedName("ImageWidth")
    @Expose
    var imageWidth: Int? = null

}