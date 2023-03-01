package com.UMC.zipdabang.src.setting

import com.google.gson.annotations.SerializedName

data class Question_Post(

    @SerializedName("email") var email : String?,
    @SerializedName("text") var text : String?

)


data class Question_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : QuestArray?,

    @SerializedName("error")
    var error: Any?

)

data class QuestArray(

    @SerializedName("userId")
    var questid : Int? ,

    @SerializedName("email")
    var questemail:  String?,

    @SerializedName("text")
    var questtext : String?

)



data class Questionlist_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Array<Questionlist>?,

    @SerializedName("error")
    var error: Any?

)

data class Questionlist(

    @SerializedName("id")
    var id : Int,

    @SerializedName("body")
    var body : String?,

    @SerializedName("created_at")
    var created_at: String

)

data class QuestionDetail_Response(

    @SerializedName("success")
    var success: Boolean?,

    @SerializedName("data")
    var data : Array<QuestionDetaillist>?,

    @SerializedName("error")
    var error: Any?

)

data class QuestionDetaillist(

    @SerializedName("contact_email")
    var contact_email : String?,

    @SerializedName("body")
    var body : String?,

    @SerializedName("created_at")
    var created_at: String

)