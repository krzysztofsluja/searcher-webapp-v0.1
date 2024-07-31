package org.sluja.searcher.webapp.service.scraper.search.implementation.shop.category;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.sluja.searcher.webapp.dto.product.request.shop.category.ShopCategoryPageSearchRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.ParametrizedExceptionWithErrorCodeAndMessage;
import org.sluja.searcher.webapp.exception.product.category.CategoryPageAddressNotFoundException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class ShopCategorySearchService<T extends ScrapRequest> extends BaseSearchService<ShopCategoryPageSearchRequest> implements ISearch<ShopCategoryPageSearchRequest, T> {

    protected final LoggerMessageUtils loggerMessageUtils;

    protected ShopCategorySearchService(final LoggerMessageUtils loggerMessageUtils) {
        this.loggerMessageUtils = loggerMessageUtils;
    }

    @Override
    public List<?> search(final ShopCategoryPageSearchRequest request, T scrapRequest) throws ProductNotFoundException {
        final List<String> addresses = new ArrayList<>();
        final List<String> categoriesPageAddresses = request.getAllCategoriesPageAddresses();
        for (final String property : categoriesPageAddresses) {
            scrapRequest.setProperty(property);
            try {
                final List<String> pageAddresses = (List<String>) getScraperService().scrap(scrapRequest);
                addresses.addAll(pageAddresses);
            } catch (final ScraperIncorrectFieldException ex) {
                log.error(loggerMessageUtils.getErrorLogMessage(LoggerUtils.getCurrentClassName(),
                        LoggerUtils.getCurrentMethodName(),
                        ex.getMessageCode(),
                        ex.getErrorCode()));
                if(!(categoriesPageAddresses.indexOf(property) == (categoriesPageAddresses.size() - 1))) {
                    log.info(loggerMessageUtils.getInfoLogMessage(LoggerUtils.getCurrentClassName(),
                            LoggerUtils.getCurrentMethodName(),
                            STR."info.shop.category.page.not.found\{ParametrizedExceptionWithErrorCodeAndMessage.SEPARATOR}\{property}"));
                    continue;
                }
                throw new CategoryPageAddressNotFoundException();
            }
        }
        if(CollectionUtils.isEmpty(addresses)) {
            throw new CategoryPageAddressNotFoundException();
        }
        return addresses;
    }

}
