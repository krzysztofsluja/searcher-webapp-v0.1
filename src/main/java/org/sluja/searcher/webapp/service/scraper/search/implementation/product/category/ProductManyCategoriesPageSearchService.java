package org.sluja.searcher.webapp.service.scraper.search.implementation.product.category;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

public abstract class ProductManyCategoriesPageSearchService<T extends ScrapRequest, S extends SearchServiceRequest>  extends BaseSearchService<S> implements ISearch<S,T> {

}
