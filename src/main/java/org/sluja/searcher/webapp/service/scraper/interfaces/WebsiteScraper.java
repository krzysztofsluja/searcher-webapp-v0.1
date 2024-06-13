package org.sluja.searcher.webapp.service.scraper.interfaces;

import org.sluja.searcher.webapp.dto.marker.scraper.IScrapRequest;
import org.sluja.searcher.webapp.dto.marker.scraper.IScraper;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;

import java.util.List;

public interface WebsiteScraper<T,S extends IScrapRequest> extends IScraper {

    List<T> scrap(S request) throws ScraperIncorrectFieldException;
}
