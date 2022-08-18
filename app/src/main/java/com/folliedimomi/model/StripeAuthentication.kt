package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class StripeAuthentication(@SerializedName("publishableKey")
                                val publishableKey: String = "",
                                @SerializedName("clientSecret")
                                val clientSecret: String = "")


