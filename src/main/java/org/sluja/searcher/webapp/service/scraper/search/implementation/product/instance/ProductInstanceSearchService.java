package org.sluja.searcher.webapp.service.scraper.search.implementation.product.instance;

import org.sluja.searcher.webapp.dto.product.request.search.SearchServiceRequest;
import org.sluja.searcher.webapp.dto.scraper.ScrapRequest;
import org.sluja.searcher.webapp.service.interfaces.search.ISearch;
import org.sluja.searcher.webapp.service.scraper.search.BaseSearchService;

public abstract class ProductInstanceSearchService<T extends ScrapRequest, S extends SearchServiceRequest> extends BaseSearchService<S> implements ISearch<S,T> {

}
