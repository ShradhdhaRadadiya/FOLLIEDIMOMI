package com.folliedimomi.model


import com.google.gson.annotations.SerializedName

data class PaypalPaymentModelTest(
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("transaction")
    var transaction: Transaction
) {
    data class Transaction(
        @SerializedName("accountFundingTransaction")
        var accountFundingTransaction: Boolean,
        @SerializedName("acquirerReferenceNumber")
        var acquirerReferenceNumber: Any?,
        @SerializedName("addOns")
        var addOns: List<Any>,
        @SerializedName("additionalProcessorResponse")
        var additionalProcessorResponse: Any?,
        @SerializedName("amount")
        var amount: String,
        @SerializedName("amountRequested")
        var amountRequested: String,
        @SerializedName("authorizationAdjustments")
        var authorizationAdjustments: List<Any>,
        @SerializedName("authorizationExpiresAt")
        var authorizationExpiresAt: AuthorizationExpiresAt,
        @SerializedName("authorizedTransactionGlobalId")
        var authorizedTransactionGlobalId: Any?,
        @SerializedName("authorizedTransactionId")
        var authorizedTransactionId: Any?,
        @SerializedName("avsErrorResponseCode")
        var avsErrorResponseCode: Any?,
        @SerializedName("avsPostalCodeResponseCode")
        var avsPostalCodeResponseCode: String,
        @SerializedName("avsStreetAddressResponseCode")
        var avsStreetAddressResponseCode: String,
        @SerializedName("billing")
        var billing: Billing,
        @SerializedName("billingDetails")
        var billingDetails: BillingDetails,
        @SerializedName("channel")
        var channel: Any?,
        @SerializedName("createdAt")
        var createdAt: CreatedAt,
        @SerializedName("creditCard")
        var creditCard: CreditCard,
        @SerializedName("creditCardDetails")
        var creditCardDetails: CreditCardDetails,
        @SerializedName("currencyIsoCode")
        var currencyIsoCode: String,
        @SerializedName("customFields")
        var customFields: Any?,
        @SerializedName("customer")
        var customer: Customer,
        @SerializedName("customerDetails")
        var customerDetails: CustomerDetails,
        @SerializedName("cvvResponseCode")
        var cvvResponseCode: String,
        @SerializedName("debitNetwork")
        var debitNetwork: Any?,
        @SerializedName("descriptor")
        var descriptor: Descriptor,
        @SerializedName("disbursementDetails")
        var disbursementDetails: DisbursementDetails,
        @SerializedName("discountAmount")
        var discountAmount: Any?,
        @SerializedName("discounts")
        var discounts: List<Any>,
        @SerializedName("disputes")
        var disputes: List<Any>,
        @SerializedName("escrowStatus")
        var escrowStatus: Any?,
        @SerializedName("gatewayRejectionReason")
        var gatewayRejectionReason: Any?,
        @SerializedName("globalId")
        var globalId: String,
        @SerializedName("id")
        var id: String,
        @SerializedName("installmentCount")
        var installmentCount: Any?,
        @SerializedName("installments")
        var installments: List<Any>,
        @SerializedName("masterMerchantAccountId")
        var masterMerchantAccountId: Any?,
        @SerializedName("merchantAccountId")
        var merchantAccountId: String,
        @SerializedName("merchantAddress")
        var merchantAddress: MerchantAddress,
        @SerializedName("merchantIdentificationNumber")
        var merchantIdentificationNumber: String,
        @SerializedName("merchantName")
        var merchantName: String,
        @SerializedName("networkResponseCode")
        var networkResponseCode: String,
        @SerializedName("networkResponseText")
        var networkResponseText: String,
        @SerializedName("networkTransactionId")
        var networkTransactionId: String,
        @SerializedName("orderId")
        var orderId: Any?,
        @SerializedName("partialSettlementTransactionGlobalIds")
        var partialSettlementTransactionGlobalIds: List<Any>,
        @SerializedName("partialSettlementTransactionIds")
        var partialSettlementTransactionIds: List<Any>,
        @SerializedName("paymentInstrumentType")
        var paymentInstrumentType: String,
        @SerializedName("paymentReceipt")
        var paymentReceipt: PaymentReceipt,
        @SerializedName("pinVerified")
        var pinVerified: Boolean,
        @SerializedName("planId")
        var planId: Any?,
        @SerializedName("processedWithNetworkToken")
        var processedWithNetworkToken: Boolean,
        @SerializedName("processingMode")
        var processingMode: Any?,
        @SerializedName("processorAuthorizationCode")
        var processorAuthorizationCode: String,
        @SerializedName("processorResponseCode")
        var processorResponseCode: String,
        @SerializedName("processorResponseText")
        var processorResponseText: String,
        @SerializedName("processorResponseType")
        var processorResponseType: String,
        @SerializedName("processorSettlementResponseCode")
        var processorSettlementResponseCode: Any?,
        @SerializedName("processorSettlementResponseText")
        var processorSettlementResponseText: Any?,
        @SerializedName("purchaseOrderNumber")
        var purchaseOrderNumber: Any?,
        @SerializedName("recurring")
        var recurring: Boolean,
        @SerializedName("refundGlobalIds")
        var refundGlobalIds: List<Any>,
        @SerializedName("refundId")
        var refundId: Any?,
        @SerializedName("refundIds")
        var refundIds: List<Any>,
        @SerializedName("refundedInstallments")
        var refundedInstallments: List<Any>,
        @SerializedName("refundedTransactionGlobalId")
        var refundedTransactionGlobalId: Any?,
        @SerializedName("refundedTransactionId")
        var refundedTransactionId: Any?,
        @SerializedName("responseEmvData")
        var responseEmvData: Any?,
        @SerializedName("retriedTransactionGlobalId")
        var retriedTransactionGlobalId: Any?,
        @SerializedName("retriedTransactionId")
        var retriedTransactionId: Any?,
        @SerializedName("retrievalReferenceNumber")
        var retrievalReferenceNumber: String,
        @SerializedName("retryGlobalIds")
        var retryGlobalIds: List<Any>,
        @SerializedName("retryIds")
        var retryIds: List<Any>,
        @SerializedName("scaExemptionRequested")
        var scaExemptionRequested: Any?,
        @SerializedName("serviceFeeAmount")
        var serviceFeeAmount: Any?,
        @SerializedName("settlementBatchId")
        var settlementBatchId: Any?,
        @SerializedName("shipping")
        var shipping: Shipping,
        @SerializedName("shippingAmount")
        var shippingAmount: Any?,
        @SerializedName("shippingDetails")
        var shippingDetails: ShippingDetails,
        @SerializedName("shipsFromPostalCode")
        var shipsFromPostalCode: Any?,
        @SerializedName("status")
        var status: String,
        @SerializedName("statusHistory")
        var statusHistory: List<StatusHistory>,
        @SerializedName("subMerchantAccountId")
        var subMerchantAccountId: Any?,
        @SerializedName("subscription")
        var subscription: Subscription,
        @SerializedName("subscriptionDetails")
        var subscriptionDetails: SubscriptionDetails,
        @SerializedName("subscriptionId")
        var subscriptionId: Any?,
        @SerializedName("taxAmount")
        var taxAmount: Any?,
        @SerializedName("taxExempt")
        var taxExempt: Boolean,
        @SerializedName("terminalIdentificationNumber")
        var terminalIdentificationNumber: String,
        @SerializedName("threeDSecureInfo")
        var threeDSecureInfo: Any?,
        @SerializedName("type")
        var type: String,
        @SerializedName("updatedAt")
        var updatedAt: UpdatedAt,
        @SerializedName("voiceReferralNumber")
        var voiceReferralNumber: Any?
    ) {
        data class AuthorizationExpiresAt(
            @SerializedName("date")
            var date: String,
            @SerializedName("timezone")
            var timezone: String,
            @SerializedName("timezone_type")
            var timezoneType: Int
        )

        data class Billing(
            @SerializedName("company")
            var company: Any?,
            @SerializedName("countryCodeAlpha2")
            var countryCodeAlpha2: Any?,
            @SerializedName("countryCodeAlpha3")
            var countryCodeAlpha3: Any?,
            @SerializedName("countryCodeNumeric")
            var countryCodeNumeric: Any?,
            @SerializedName("countryName")
            var countryName: Any?,
            @SerializedName("extendedAddress")
            var extendedAddress: Any?,
            @SerializedName("firstName")
            var firstName: Any?,
            @SerializedName("id")
            var id: Any?,
            @SerializedName("lastName")
            var lastName: Any?,
            @SerializedName("locality")
            var locality: Any?,
            @SerializedName("postalCode")
            var postalCode: Any?,
            @SerializedName("region")
            var region: Any?,
            @SerializedName("streetAddress")
            var streetAddress: Any?
        )

        class BillingDetails

        data class CreatedAt(
            @SerializedName("date")
            var date: String,
            @SerializedName("timezone")
            var timezone: String,
            @SerializedName("timezone_type")
            var timezoneType: Int
        )

        data class CreditCard(
            @SerializedName("accountBalance")
            var accountBalance: Any?,
            @SerializedName("accountType")
            var accountType: String,
            @SerializedName("bin")
            var bin: String,
            @SerializedName("cardType")
            var cardType: String,
            @SerializedName("cardholderName")
            var cardholderName: Any?,
            @SerializedName("commercial")
            var commercial: String,
            @SerializedName("countryOfIssuance")
            var countryOfIssuance: String,
            @SerializedName("customerLocation")
            var customerLocation: String,
            @SerializedName("debit")
            var debit: String,
            @SerializedName("durbinRegulated")
            var durbinRegulated: String,
            @SerializedName("expirationMonth")
            var expirationMonth: String,
            @SerializedName("expirationYear")
            var expirationYear: String,
            @SerializedName("globalId")
            var globalId: Any?,
            @SerializedName("healthcare")
            var healthcare: String,
            @SerializedName("imageUrl")
            var imageUrl: String,
            @SerializedName("issuingBank")
            var issuingBank: String,
            @SerializedName("last4")
            var last4: String,
            @SerializedName("payroll")
            var payroll: String,
            @SerializedName("prepaid")
            var prepaid: String,
            @SerializedName("productId")
            var productId: String,
            @SerializedName("token")
            var token: Any?,
            @SerializedName("uniqueNumberIdentifier")
            var uniqueNumberIdentifier: Any?,
            @SerializedName("venmoSdk")
            var venmoSdk: Boolean
        )

        class CreditCardDetails

        data class Customer(
            @SerializedName("company")
            var company: Any?,
            @SerializedName("email")
            var email: Any?,
            @SerializedName("fax")
            var fax: Any?,
            @SerializedName("firstName")
            var firstName: Any?,
            @SerializedName("id")
            var id: Any?,
            @SerializedName("lastName")
            var lastName: Any?,
            @SerializedName("phone")
            var phone: Any?,
            @SerializedName("website")
            var website: Any?
        )

        class CustomerDetails

        class Descriptor

        class DisbursementDetails

        data class MerchantAddress(
            @SerializedName("locality")
            var locality: String,
            @SerializedName("phone")
            var phone: String,
            @SerializedName("postalCode")
            var postalCode: String,
            @SerializedName("region")
            var region: String,
            @SerializedName("streetAddress")
            var streetAddress: Any?
        )

        data class PaymentReceipt(
            @SerializedName("amount")
            var amount: String,
            @SerializedName("cardLast4")
            var cardLast4: String,
            @SerializedName("cardType")
            var cardType: String,
            @SerializedName("currencyIsoCode")
            var currencyIsoCode: String,
            @SerializedName("globalId")
            var globalId: String,
            @SerializedName("id")
            var id: String,
            @SerializedName("merchantAddress")
            var merchantAddress: MerchantAddress,
            @SerializedName("merchantIdentificationNumber")
            var merchantIdentificationNumber: String,
            @SerializedName("merchantName")
            var merchantName: String,
            @SerializedName("pinVerified")
            var pinVerified: Boolean,
            @SerializedName("processingMode")
            var processingMode: Any?,
            @SerializedName("processorAuthorizationCode")
            var processorAuthorizationCode: String,
            @SerializedName("processorResponseCode")
            var processorResponseCode: String,
            @SerializedName("processorResponseText")
            var processorResponseText: String,
            @SerializedName("terminalIdentificationNumber")
            var terminalIdentificationNumber: String,
            @SerializedName("type")
            var type: String
        ) {
            data class MerchantAddress(
                @SerializedName("locality")
                var locality: String,
                @SerializedName("phone")
                var phone: String,
                @SerializedName("postalCode")
                var postalCode: String,
                @SerializedName("region")
                var region: String,
                @SerializedName("streetAddress")
                var streetAddress: Any?
            )
        }

        data class Shipping(
            @SerializedName("company")
            var company: Any?,
            @SerializedName("countryCodeAlpha2")
            var countryCodeAlpha2: Any?,
            @SerializedName("countryCodeAlpha3")
            var countryCodeAlpha3: Any?,
            @SerializedName("countryCodeNumeric")
            var countryCodeNumeric: Any?,
            @SerializedName("countryName")
            var countryName: Any?,
            @SerializedName("extendedAddress")
            var extendedAddress: Any?,
            @SerializedName("firstName")
            var firstName: Any?,
            @SerializedName("id")
            var id: Any?,
            @SerializedName("lastName")
            var lastName: Any?,
            @SerializedName("locality")
            var locality: Any?,
            @SerializedName("postalCode")
            var postalCode: Any?,
            @SerializedName("region")
            var region: Any?,
            @SerializedName("streetAddress")
            var streetAddress: Any?
        )

        class ShippingDetails

        class StatusHistory

        data class Subscription(
            @SerializedName("billingPeriodEndDate")
            var billingPeriodEndDate: Any?,
            @SerializedName("billingPeriodStartDate")
            var billingPeriodStartDate: Any?
        )

        class SubscriptionDetails

        data class UpdatedAt(
            @SerializedName("date")
            var date: String,
            @SerializedName("timezone")
            var timezone: String,
            @SerializedName("timezone_type")
            var timezoneType: Int
        )
    }
}