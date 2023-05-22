package org.example.order;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.cart.*;
import com.commercetools.api.models.common.AddressDraftBuilder;
import com.commercetools.api.models.order.Order;
import com.commercetools.api.models.order.OrderFromCartDraft;
import com.commercetools.api.models.order.OrderFromCartDraftBuilder;

public class OrderAPI {

    public Order createOrder(Cart cart, ProjectApiRoot apiRoot){

        CartSetShippingAddressAction cartsetShippingAddressAction = CartSetShippingAddressActionBuilder.of()
                .address(AddressDraftBuilder.of().country("DE").build())
                .build();

        CartUpdate cartUpdate = CartUpdateBuilder.of()
                .actions(cartsetShippingAddressAction)
                .version(cart.getVersion()).build();

        Cart newCart = apiRoot.carts().withId(cart.getId()).post(cartUpdate)
                .executeBlocking().getBody();

        OrderFromCartDraft orderFromCartDraft = OrderFromCartDraftBuilder.of()
                .cart(CartResourceIdentifierBuilder.of()
                        .id(newCart.getId()).build())
                .version(newCart.getVersion())
                .build();

        return apiRoot.orders()
                .post(orderFromCartDraft)
                .executeBlocking().getBody();

    }
}
