package org.example.myCustomer;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.*;

public class MyCustomerAPIActions {

    public Customer resetPassword(ProjectApiRoot apiRoot, String email) {

        CustomerCreatePasswordResetToken customerCreatePasswordResetToken = CustomerCreatePasswordResetTokenBuilder.of()
                .email(email)
                .ttlMinutes(10l).build();
        CustomerToken customerToken = apiRoot.customers()
                .passwordToken()
                .post(customerCreatePasswordResetToken)
                .executeBlocking().getBody();


        MyCustomerResetPassword myCustomerResetPassword = MyCustomerResetPasswordBuilder.of()
                .tokenValue(customerToken.getValue())
                .newPassword("abc@2023")
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
