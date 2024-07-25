package org.sluja.searcher.webapp.dto.product.request.get;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.sluja.searcher.webapp.dto.marker.product.request.SearchServiceRequestMarker;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetProductForManyShopsAndCategoriesRequest implements SearchServiceRequestMarker, Serializable {

    private Map<String, List<String>> shopsWithCategories;
    private Map<String, GetProductForShopNameRequest> shopsPropertiesMap;
    @NotEmpty(message = "error.validation.context.empty")
    private String context;
}
