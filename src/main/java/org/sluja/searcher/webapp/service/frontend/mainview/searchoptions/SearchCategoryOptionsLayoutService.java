package org.sluja.searcher.webapp.service.frontend.mainview.searchoptions;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.event.mainview.SearchProductsParameterCategoryRangeEvent;
import org.sluja.searcher.webapp.frontend.components.main.category.options.ISearchCategoryOptionsLayout;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchCategoryOptionsLayoutService {

    private final ApplicationEventPublisher eventPublisher;

    public void publishSearchOptionsChangedEvent(final ISearchCategoryOptionsLayout layout, final boolean value) {
        eventPublisher.publishEvent(new SearchProductsParameterCategoryRangeEvent(layout, value));
    }
}
