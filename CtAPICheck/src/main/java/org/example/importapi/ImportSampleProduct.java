package org.example.importapi;

import com.commercetools.importapi.client.ProjectApiRoot;
import com.commercetools.importapi.models.common.LocalizedStringBuilder;
import com.commercetools.importapi.models.common.ProductTypeKeyReferenceBuilder;
import com.commercetools.importapi.models.customers.CustomerImportBuilder;
import com.commercetools.importapi.models.importcontainers.ImportContainer;
import com.commercetools.importapi.models.importrequests.CustomerImportRequestBuilder;
import com.commercetools.importapi.models.importrequests.ImportResponse;
import com.commercetools.importapi.models.importrequests.ProductDraftImportRequest;
import com.commercetools.importapi.models.importrequests.ProductDraftImportRequestBuilder;
import com.commercetools.importapi.models.productdrafts.ProductDraftImportBuilder;

public class ImportSampleProduct {
    public ImportResponse importProduct(ProjectApiRoot importApiRoot, ImportContainer container) {
        ProductDraftImportRequest productDraftImportRequest = ProductDraftImportRequestBuilder.of()
                .plusResources(ProductDraftImportBuilder.of()
                        .key("sampleImportedProduct-3")
                        .description(LocalizedStringBuilder.of().addValue("en", "Sample imported product 2").build())
                        .productType(ProductTypeKeyReferenceBuilder.of()
                                .key("sample-product-type")
                                .build())
                        .name(LocalizedStringBuilder.of().addValue("en","My imported product-2").build())
                        .slug(LocalizedStringBuilder.of().addValue("en","My-imported-product-slug-1").build())
                        .build())
                .build();


        return importApiRoot.productDrafts().importContainers().withImportContainerKeyValue(container.getKey())
                .post(productDraftImportRequest)
                .executeBlocking().getBody();
    }
}
