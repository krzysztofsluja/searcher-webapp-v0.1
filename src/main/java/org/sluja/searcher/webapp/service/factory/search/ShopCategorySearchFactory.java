package org.sluja.searcher.webapp.service.factory.search;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("shopCategorySearchFactory")
@RequiredArgsConstructor
public class ShopCategorySearchFactory implements ISearchFactory<ShopCategorySearchService<?>> {

    private final ShopCategorySearchService<StaticWebsiteScrapRequest> staticWebsiteShopCategorySearchService;
    @Override
    public ShopCategorySearchService<?> createSearch(final boolean isDynamicWebsite) {
        return isDynamicWebsite ? null : staticWebsiteShopCategorySearchService;
    }
}
