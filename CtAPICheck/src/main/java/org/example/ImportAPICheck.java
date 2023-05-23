package org.example;

import com.commercetools.importapi.client.ProjectApiRoot;
import com.commercetools.importapi.defaultconfig.ImportApiRootBuilder;
import com.commercetools.importapi.defaultconfig.ServiceRegion;
import com.commercetools.importapi.models.common.ImportResourceType;
import com.commercetools.importapi.models.importcontainers.ImportContainer;
import com.commercetools.importapi.models.importcontainers.ImportContainerDraftBuilder;
import com.commercetools.importapi.models.importrequests.ImportResponse;
import io.vrap.rmf.base.client.oauth2.ClientCredentials;
import org.example.importapi.ImportSampleProduct;

import java.util.Objects;

public class ImportAPICheck {
    public static void main(String[] args) {
        ProjectApiRoot importApiRoot = getImportApiRoot();
        ImportContainer container = createContainer(importApiRoot);

        ImportSampleProduct importSampleProduct = new ImportSampleProduct();
        ImportResponse importResponse = importSampleProduct.importProduct(importApiRoot, container);


        System.out.println("Testing import container creation");
    }

    private static ImportContainer createContainer(ProjectApiRoot importApiRoot) {

        ImportContainer importContainer;
        try{
            importContainer = importApiRoot.importContainers()
                    .withImportContainerKeyValue("Sample-Key")
                    .get()
                    .executeBlocking().getBody();
        } catch (Exception e){
            System.out.println("Handling exception");
            importContainer = importApiRoot.importContainers()
                    .post(ImportContainerDraftBuilder.of()
                            .key("Sample-Key")
                            .resourceType(ImportResourceType.PRODUCT_DRAFT)
                            .build())
                    .executeBlocking().getBody();
        }

        return importContainer;

    }

    private static ProjectApiRoot getImportApiRoot() {
        return ImportApiRootBuilder.of()
                .defaultClient(ClientCredentials.of()
                        .withClientId("xUH4sYQlQmemYo6IYtdMEITh")
                        .withClientSecret("0aTql8m1CppFTXz1dMSKXjYkNnsFuA7o")
                        .build(), ServiceRegion.GCP_AUSTRALIA_SOUTHEAST1)
                .build("practiceproject-2023");

    }
}
