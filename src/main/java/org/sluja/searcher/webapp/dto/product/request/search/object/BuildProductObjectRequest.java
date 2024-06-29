package org.sluja.searcher.webapp.dto.product.request.search.object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jsoup.nodes.Element;


import java.util.List;

@Getter
@AllArgsConstructor
public class BuildProductObjectRequest {

    private String productPrice;
    private String homePageAddress;
    private String productName;
    private String productDiscountPrice;
    private String productImageExtractAttribute;
    private String div;
    private String pageAddressExtractAttribute;
    private String plainPageAddressToFormat;
    private List<Element> instancesToCreateProducts;
    private List<String> productPageAddresses;
    private List<String> productImageAddresses;
    private String shopName;
    private String categoryName;
}
