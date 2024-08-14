package org.sluja.searcher.webapp.dto.event.mainview;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

public class SearchProductsParameterCategoryRangeEvent extends ApplicationEvent {

    @Getter
    private final boolean sameCategoryForEachShop;
    public SearchProductsParameterCategoryRangeEvent(final Object source, final boolean sameCategoryForEachShop) {
        super(source);
        this.sameCategoryForEachShop = sameCategoryForEachShop;
    }
}
