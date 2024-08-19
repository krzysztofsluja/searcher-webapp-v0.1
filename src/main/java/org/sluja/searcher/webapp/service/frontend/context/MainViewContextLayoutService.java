package org.sluja.searcher.webapp.service.frontend.context;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.session.MainViewSearchRequestSessionAttribute;
import org.sluja.searcher.webapp.dto.session.frontend.mainview.MainViewSearchContextForUserSessionAttribute;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MainViewContextLayoutService {

    private final MainViewSearchContextForUserSessionAttribute mainViewSearchContextForUserSessionAttribute;
    private final MainViewSearchRequestSessionAttribute mainViewSearchRequestSessionAttribute;

    public void setContext(final String context) {
        mainViewSearchContextForUserSessionAttribute.setCategoryContext(context);
        mainViewSearchContextForUserSessionAttribute.setCategoryContext(context);
    }

    public void refreshShopsWithCategoriesLayout() {
        if(Objects.nonNull(mainViewSearchContextForUserSessionAttribute.getMainView())) {
            mainViewSearchContextForUserSessionAttribute.getMainView().refreshShopsWithCategoriesLayout();
        }
    }
}
