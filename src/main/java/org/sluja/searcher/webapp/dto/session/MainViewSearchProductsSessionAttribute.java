package org.sluja.searcher.webapp.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.sluja.searcher.webapp.frontend.route.main.MainView;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@SessionScope
@Setter
@Getter
@ToString
public class MainViewSearchProductsSessionAttribute {

    private boolean otherCategoryForEachShop;
    private Map<String, Boolean> otherShopForEachCategoryCategoryLayoutShowed = new HashMap<>();
    private Set<String> selectedShopNames = new HashSet<>();
    private String actuallyClickedCategoryShopButton;
    private String context;
    private MainView mainView;

    public void setOtherCategoryLayoutVisibilityForEachShop() {
        if(!otherShopForEachCategoryCategoryLayoutShowed.keySet().isEmpty()) {
            otherShopForEachCategoryCategoryLayoutShowed
                    .keySet()
                    .forEach(category -> otherShopForEachCategoryCategoryLayoutShowed.put(category, false));
        }
    }
}
