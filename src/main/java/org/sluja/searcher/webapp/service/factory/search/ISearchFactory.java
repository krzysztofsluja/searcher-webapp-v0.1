package org.sluja.searcher.webapp.service.factory.search;

public interface ISearchFactory<T> {

    T createSearch(final boolean isDynamicWebsite);
}
