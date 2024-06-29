package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.exception.enums.search.ValueForSearchPropertyException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.scraper.search.IncorrectInputException;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

import java.util.List;

public abstract class ProductCategoryPageSearchService<T extends ScrapRequest, S extends SearchServiceRequest> extends BaseSearchService<S> implements ISearch<S,T> {

    protected boolean doesPageContainProductCategoryName(final String pageAddress, final String categoryName) {
        return StringUtils.isNotEmpty(pageAddress)
                && StringUtils.isNotEmpty(categoryName)
                && pageAddress.toUpperCase().contains(categoryName.toUpperCase());
    }

    public boolean doesPageContainProductCategoryName(final String pageAddress, final List<String> categoryProperties) throws ValueForSearchPropertyException {
        return categoryProperties.stream()
                .anyMatch(property -> doesPageContainProductCategoryName(pageAddress, property));
    }

    protected boolean doesPageHaveNextInOrder(final String currentCategoryPageAddress) throws IncorrectInputException {
        if(StringUtils.isEmpty(currentCategoryPageAddress) && !StringUtils.isNumeric(currentCategoryPageAddress)) {
            throw new IncorrectInputException("error.current.page.notFound");
        }
        return currentCategoryPageAddress.equals(String.valueOf(getNumberOfCurrentPage(currentCategoryPageAddress) + 1));
    }

    protected int getNumberOfCurrentPage(final String currentCategoryPageAddress) {
        return Integer.parseInt(currentCategoryPageAddress);
    }

    public abstract List<?> getCategoryPageAddresses(S request) throws ProductNotFoundException;
    public abstract List<?> getAllCategoriesAddresses(S request) throws ProductNotFoundException;

}
