package org.sluja.searcher.webapp.service.factory.search;

import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteScrapRequest;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.ShopCategorySearchService;
import org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category.stat.StaticWebsiteShopCategorySearchService;

public abstract class ShopCategorySearchFactory {

    public static ShopCategorySearchService<?> createSearch(final boolean isDynamicWebsite) {
        return new StaticWebsiteShopCategorySearchService();
    }
}
