package org.example.payment;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.common.MoneyBuilder;
import com.commercetools.api.models.common.TypedMoney;
import com.commercetools.api.models.payment.*;

import java.util.UUID;

public class PaymentAPI {

    public PaymentDraft createPaymentDraft(TypedMoney totalPrice){
        PaymentDraft paymentDraft = PaymentDraftBuilder.of()
                .amountPlanned(MoneyBuilder.of()
                        .centAmount(totalPrice.getCentAmount())
                        .currencyCode(totalPrice.getCurrencyCode())
                        .build())
                .paymentMethodInfo(PaymentMethodInfoBuilder.of()
                        .method("CASH")
                        .build())
                .interfaceId(UUID.randomUUID().toString())
                /*.transactions(TransactionDraftBuilder.of()
                        .amount(MoneyBuilder.of()
                                .centAmount(totalPrice.getCentAmount())
                                .currencyCode(totalPrice.getCurrencyCode())
                                .build())
                        .state(TransactionState.INITIAL)
                        .type(TransactionType.CHARGE)
                        .build())*/
                .build();
        return paymentDraft;
    }

    public Payment createPayment(PaymentDraft paymentDraft, ProjectApiRoot apiRoot){
        return apiRoot.payments()
                .post(paymentDraft)
                .executeBlocking().getBody();

    }
}
