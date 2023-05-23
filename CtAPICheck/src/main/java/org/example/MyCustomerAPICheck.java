package org.example;

import com.commercetools.api.client.ApiRoot;
import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.customer.Customer;
import io.vrap.rmf.base.client.AuthenticationToken;
import io.vrap.rmf.base.client.HttpClientSupplier;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import io.vrap.rmf.base.client.oauth2.ClientCredentialsImpl;
import io.vrap.rmf.base.client.oauth2.GlobalCustomerPasswordTokenSupplier;
import io.vrap.rmf.base.client.utils.ClientUtils;
import org.example.myCustomer.MyCustomerAPIActions;

import java.time.Duration;

public class MyCustomerAPICheck {
    public static void main(String[] args) {
        ProjectApiRoot accessToken = getAccessToken();

        MyCustomerAPIActions myCustomerAPIActions = new MyCustomerAPIActions();
        Customer me = myCustomerAPIActions.getMe(accessToken);
        Customer changePassword = myCustomerAPIActions.changePassword(accessToken, me);
        //Customer resetPassword = myCustomerAPIActions.resetPassword(accessToken);
        System.out.println("Testing access token");
    }

    private static ProjectApiRoot getAccessToken() {

        /*final ClientCredentials credentials = ClientCredentials.of()
                .withClientId("xUH4sYQlQmemYo6IYtdMEITh")
                .withClientSecret("0aTql8m1CppFTXz1dMSKXjYkNnsFuA7o")
                .build();
        GlobalCustomerPasswordTokenSupplier supplier = new GlobalCustomerPasswordTokenSupplier(
                credentials.getClientId(), credentials.getClientSecret(), "dinesh.tharayil@valtech.com",
                "June@2021", null,
                ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getPasswordFlowTokenURL("practiceproject-2023"),
                HttpClientSupplier.of().get());
        final AuthenticationToken token = ClientUtils.blockingWait(supplier.getToken(), Duration.ofSeconds(10));*/

        ProjectApiRoot apiRoot = ApiRootBuilder.of()
                .withApiBaseUrl("https://api.australia-southeast1.gcp.commercetools.com")
                .withGlobalCustomerPasswordFlow((ClientCredentials.of()
                        .withClientId("xUH4sYQlQmemYo6IYtdMEITh")
                        .withClientSecret("0aTql8m1CppFTXz1dMSKXjYkNnsFuA7o")
                        .build()), "abc@yahoo.com", "abc@2023", "https://auth.australia-southeast1.gcp.commercetools.com/oauth/practiceproject-2023/customers/token")
                .build("practiceproject-2023");

        System.out.println("test");
        return apiRoot;

    }
}
