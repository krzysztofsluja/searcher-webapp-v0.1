package org.sluja.searcher.webapp.dto.event.mainview.listener;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.event.mainview.SearchProductsParameterCategoryRangeEvent;
import org.sluja.searcher.webapp.dto.session.MainViewSearchProductsSessionAttribute;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MainViewSearchProductsSessionAttributeEventListener {

    private final MainViewSearchProductsSessionAttribute mainViewSearchProductsSessionAttribute;

    @EventListener
    public void handleSearchProductsParameterCategoryRangeEvent(final SearchProductsParameterCategoryRangeEvent event) {
       if(Objects.nonNull(event)) {
           mainViewSearchProductsSessionAttribute.setSameCategoryForEachShop(event.isSameCategoryForEachShop());
       }
    }
}
