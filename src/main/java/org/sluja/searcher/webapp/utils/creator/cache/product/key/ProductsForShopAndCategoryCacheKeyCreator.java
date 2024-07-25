package org.sluja.searcher.webapp.utils.creator.cache.product.key;

import org.sluja.searcher.webapp.dto.creator.product.ProductsForShopAndCategoryKeyCreatorData;
import org.sluja.searcher.webapp.exception.cache.CacheKeyCreationFailedException;
import org.sluja.searcher.webapp.utils.creator.Creator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Qualifier("productsForShopAndCategoryCacheKeyCreator")
public class ProductsForShopAndCategoryCacheKeyCreator implements Creator<String, ProductsForShopAndCategoryKeyCreatorData> {

    private static final String SEPARATOR = "-";

    @Override
    public String create(final ProductsForShopAndCategoryKeyCreatorData request) throws CacheKeyCreationFailedException {
        if(Objects.nonNull(request)) {
            return new StringBuilder()
                    .append(request.shopName())
                    .append(SEPARATOR)
                    .append(request.category())
                    .append(SEPARATOR)
                    .append(request.context())
                    .append(SEPARATOR)
                    .append(request.additionDate().getYear())
                    .append(SEPARATOR)
                    .append(request.additionDate().getMonthValue())
                    .append(SEPARATOR)
                    .append(request.additionDate().getDayOfMonth())
                    .toString();
        }
        throw new CacheKeyCreationFailedException();
    }
}
