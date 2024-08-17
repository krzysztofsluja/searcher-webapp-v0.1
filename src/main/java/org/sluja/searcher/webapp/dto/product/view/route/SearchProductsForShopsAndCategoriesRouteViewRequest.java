package org.sluja.searcher.webapp.dto.product.view.route;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
import java.util.Map;

@Component
@SessionScope
@Setter
@Getter
@ToString
public class SearchProductsForShopsAndCategoriesRouteViewRequest {

    private String id;
    private Map<String, List<String>> shopsWithCategories;
    private String context;
}
