package org.example;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.cart.Cart;
import com.commercetools.api.models.customer.CustomerPagedQueryResponse;
import com.commercetools.api.models.customer.CustomerSignInResult;
import com.commercetools.api.models.order.Order;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import org.example.cart.CartAPI;
import org.example.customer.CustomerAPI;
import org.example.order.OrderAPI;

public class CtApiCheck {
    public static void main(String[] args) {
        ProjectApiRoot apiRoot = getApiRoot();

        /*CustomerAPI customerAPI = new CustomerAPI();
        CustomerPagedQueryResponse customers = customerAPI.getCustomers(apiRoot);
        System.out.println("Testing");*/

        CartAPI cartAPI = new CartAPI();
       // Cart cart = cartAPI.addToCart("M0E20000000EEWL", "2", apiRoot);

        /*OrderAPI orderAPI = new OrderAPI();
        orderAPI.createOrder(cart, apiRoot);*/

        Cart anonCart = cartAPI.addAnonToCart("M0E20000000EEWL", "2", apiRoot);
        Cart custCart = cartAPI.addCustomerCart("M0E20000000DOO7", "2", apiRoot);
        CustomerSignInResult customerSignInResult = cartAPI.mergeAnonCart(anonCart, apiRoot);

        System.out.println("Testing");
    }

    private static ProjectApiRoot getApiRoot() {
        ProjectApiRoot apiRoot = ApiRootBuilder.of()
                .defaultClient(ClientCredentials.of()
                                .withClientId("xUH4sYQlQmemYo6IYtdMEITh")
                                .withClientSecret("0aTql8m1CppFTXz1dMSKXjYkNnsFuA7o")
                                .build(),
                        ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
                .build("practiceproject-2023");

        return apiRoot;
    }


}