package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class PaypalPaymentModel(
    @SerializedName("creditCardVerification")
    var creditCardVerification: Any?,
    @SerializedName("errors")
    var errors: List<Error>,
    @SerializedName("merchantAccount")
    var merchantAccount: Any?,
    @SerializedName("message")
    var message: String,
    @SerializedName("params")
    var params: Params,
    @SerializedName("subscription")
    var subscription: Any?,
    @SerializedName("transaction")
    var transaction: Any?,
    @SerializedName("verification")
    var verification: Any?
) {
    class Error

    data class Params(
        @SerializedName("transaction")
        var transaction: Transaction
    ) {
        data class Transaction(
            @SerializedName("amount")
            var amount: String,
            @SerializedName("correlationId")
            var correlationId: String,
            @SerializedName("options")
            var options: Options,
            @SerializedName("paymentMethodNonce")
            var paymentMethodNonce: String,
            @SerializedName("type")
            var type: String
        ) {
            data class Options(
                @SerializedName("submitForSettlement")
                var submitForSettlement: String
            )
        }
    }
}