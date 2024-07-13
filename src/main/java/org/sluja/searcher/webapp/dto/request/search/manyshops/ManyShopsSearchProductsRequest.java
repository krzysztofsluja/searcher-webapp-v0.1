package org.sluja.searcher.webapp.dto.request.search.manyshops;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ManyShopsSearchProductsRequest implements Serializable {

    private List<ProductScrapWithDefinedAttributes> shopsProperties;
}
