package org.example;

import com.commercetools.api.client.ProjectApiRoot;
import com.commercetools.api.defaultconfig.ApiRootBuilder;
import com.commercetools.api.defaultconfig.ServiceRegion;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;

public class ImportAPICheck {
    public static void main(String[] args) {
        ProjectApiRoot apiRoot = getApiRoot();
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
