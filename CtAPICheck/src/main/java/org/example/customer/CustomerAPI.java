package org.example.customer;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.models.customer.CustomerPagedQueryResponse;

public class CustomerAPI {
    public CustomerPagedQueryResponse getCustomers(ProjectApiRoot apiRoot){

        return apiRoot.customers()
                .get()
                .executeBlocking().getBody();
    }
}
