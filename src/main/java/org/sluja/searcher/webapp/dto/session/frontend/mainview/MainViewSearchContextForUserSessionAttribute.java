package org.sluja.searcher.webapp.dto.session.frontend.mainview;

import lombok.Data;
import org.sluja.searcher.webapp.frontend.route.main.MainView;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@SessionScope
@Data
public class MainViewSearchContextForUserSessionAttribute {

    private String categoryContext;
    private Set<String> selectedShopsNames = new HashSet<>();
    private boolean otherCategoriesForEachShop;
    private String shopForWhichCategoryLayoutIsShown;
    private Map<String, Boolean> categoryLayoutVisibilityForShop = new HashMap<>();
    private MainView mainView;
}
