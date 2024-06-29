package org.sluja.searcher.webapp.service.scraper.search.implementation.product.object;

import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;

import java.util.List;

public interface IBuildProductObject {

    List<ProductDTO> build(final BuildProductObjectRequest request) throws ProductNotFoundException;
}