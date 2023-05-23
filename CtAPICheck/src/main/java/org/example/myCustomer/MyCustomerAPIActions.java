package org.example.myCustomer;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ServiceRegion;
import com.commercetools.api.models.customer.*;
import io.vrap.rmf.base.client.AuthenticationToken;
import io.vrap.rmf.base.client.HttpClientSupplier;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import io.vrap.rmf.base.client.oauth2.GlobalCustomerPasswordTokenSupplier;
import io.vrap.rmf.base.client.utils.ClientUtils;

import java.time.Duration;

public class MyCustomerAPIActions {

    public Customer resetPassword(ProjectApiRoot apiRoot) {
        final ClientCredentials credentials = ClientCredentials.of()
                .withClientId("xUH4sYQlQmemYo6IYtdMEITh")
                .withClientSecret("0aTql8m1CppFTXz1dMSKXjYkNnsFuA7o")
                .build();
        GlobalCustomerPasswordTokenSupplier supplier = new GlobalCustomerPasswordTokenSupplier(
                credentials.getClientId(), credentials.getClientSecret(), "dinesh.tharayil@valtech.com",
                "June@2021", null,
                ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1.getPasswordFlowTokenURL("practiceproject-2023"),
                HttpClientSupplier.of().get());
        final AuthenticationToken token = ClientUtils.blockingWait(supplier.getToken(), Duration.ofSeconds(10));


        MyCustomerResetPassword myCustomerResetPassword = MyCustomerResetPasswordBuilder.of()
                .tokenValue(token.getRefreshToken())
                .newPassword("July@2021")
                .build();

        return apiRoot.me()
                .password()
                .reset()
                .post(myCustomerResetPassword)
                .executeBlocking().getBody();


    }

    public Customer getMe(ProjectApiRoot projectApiRoot){
        return projectApiRoot.me().get()
                .executeBlocking().getBody();

    }

    public Customer changePassword(ProjectApiRoot projectApiRoot, Customer me){
        MyCustomerChangePassword myCustomerChangePassword = MyCustomerChangePasswordBuilder.of()
                .currentPassword("abc@2023")
                .newPassword("abc-1@2023")
                .version(me.getVersion())
                .build();

        return projectApiRoot.me()
                .password()
                .post(myCustomerChangePassword)
                .executeBlocking().getBody();
    }
}
