package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class UpdateQty(@SerializedName("message")
                     val message: String = "",
                     @SerializedName("status")
                     val status: Int = 0)


