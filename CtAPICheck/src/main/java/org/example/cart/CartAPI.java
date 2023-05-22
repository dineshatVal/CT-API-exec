package org.example.cart;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.common.AddressDraftBuilder;
import com.commercetools.api.models.customer.AnonymousCartSignInMode;
import com.commercetools.api.models.customer.CustomerSignInResult;
import com.commercetools.api.models.customer.CustomerSigninBuilder;
import com.commercetools.api.models.payment.*;
import com.commercetools.api.models.shipping_method.ShippingMethodResourceIdentifierBuilder;
import org.example.payment.PaymentAPI;

import java.util.UUID;

public class CartAPI {

    public Cart addToCart(String sku, String qty, ProjectApiRoot apiRoot){
        LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
                .sku(sku).quantity(Long.valueOf(qty)).build();

        /*CustomLineItemDraft customLineItemDraftBuilder = CustomLineItemDraftBuilder.of()
                .quantity(2l)
                .money(TypedMoneyBuilder.of()
                        .centPrecisionBuilder()
                        .currencyCode("EUR")
                        .fractionDigits(2)
                        .centAmount(1000l).build())
                .name(LocalizedStringBuilder.of().addValue("en", "Test CustomLineItem").build())
                .slug("test-custom-lineitem-1")
                .taxCategory(TaxCategoryResourceIdentifierBuilder.of()
                        .key("standard").build())
                .build();*/

        /*CartSetShippingMethodAction cartSetShippingMethodAction = CartSetShippingMethodActionBuilder.of()
                .shippingMethod(ShippingMethodResourceIdentifierBuilder.of()
                        .key("std-EU").build()).build();*/


        CartDraft cartDraft = CartDraftBuilder.of()
                .lineItems(lineItemDraft)
                .currency("EUR")
                //.customLineItems(customLineItemDraftBuilder)
                .shippingMethod(ShippingMethodResourceIdentifierBuilder.of()
                        .key("std-EU").build())
               // .discountCodes("ABCD")
                .shippingAddress(AddressDraftBuilder.of().country("DE").build())
                .customerEmail("dinesh.tharayil@valtech.com")
                .customerId("e1adaefe-5f96-4b4e-a39b-293d9f3378ef")
                .build();


        Cart cart = apiRoot.carts()
                .post(cartDraft)
                .executeBlocking().getBody();

        PaymentAPI paymentAPI = new PaymentAPI();
        Payment payment = paymentAPI.createPayment(paymentAPI.createPaymentDraft(cart.getTotalPrice()), apiRoot);
        CartAddPaymentAction cartAddPaymentAction = CartAddPaymentActionBuilder.of()
                .payment(PaymentResourceIdentifierBuilder.of()
                        .id(payment.getId()).key(payment.getKey()).build())
                .build();

        return apiRoot.carts()
                .withId(cart.getId())
                .post(CartUpdateBuilder.of()
                        .actions(cartAddPaymentAction).version(cart.getVersion())
                        .build())
                .executeBlocking().getBody();
        /*return apiRoot.carts()
                .post(cartDraft)
                .executeBlocking().getBody();*/

    }

    public Cart addAnonToCart(String sku, String qty, ProjectApiRoot apiRoot) {
        LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
                .sku(sku).quantity(Long.valueOf(qty)).build();

        CartDraft cartDraft = CartDraftBuilder.of()
                .lineItems(lineItemDraft)
                .currency("EUR")
                .shippingMethod(ShippingMethodResourceIdentifierBuilder.of()
                        .key("std-EU").build())
                .shippingAddress(AddressDraftBuilder.of().country("DE").build())
                .anonymousId(UUID.randomUUID().toString())
                .build();

        return apiRoot.carts()
                .post(cartDraft)
                .executeBlocking().getBody();

    }

    public Cart addCustomerCart(String sku, String qty, ProjectApiRoot apiRoot) {
        LineItemDraft lineItemDraft = LineItemDraftBuilder.of()
                .sku(sku).quantity(Long.valueOf(qty)).build();

        CartDraft cartDraft = CartDraftBuilder.of()
                .lineItems(lineItemDraft)
                .currency("EUR")
                .shippingMethod(ShippingMethodResourceIdentifierBuilder.of()
                        .key("std-EU").build())
                .shippingAddress(AddressDraftBuilder.of().country("DE").build())
                .customerId("264cd6f0-2605-4f06-bf51-646d37e4b015")
                .build();

        return apiRoot.carts()
                .post(cartDraft)
                .executeBlocking().getBody();

    }

    public CustomerSignInResult mergeAnonCart(Cart anonCart, ProjectApiRoot apiRoot) {
        return apiRoot.login()
                .post(CustomerSigninBuilder.of()
                        .anonymousCart(CartResourceIdentifierBuilder.of()
                                .id(anonCart.getId()).build())
                        .anonymousCartSignInMode(AnonymousCartSignInMode.MERGE_WITH_EXISTING_CUSTOMER_CART)
                        .email("zapseyufya@gufum.com")
                        .password("June@2021")
                        .build())
                .executeBlocking().getBody();
    }
}
