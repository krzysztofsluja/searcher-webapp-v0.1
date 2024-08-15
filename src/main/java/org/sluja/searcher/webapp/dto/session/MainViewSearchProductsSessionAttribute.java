package org.sluja.searcher.webapp.dto.session;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
@Setter
@Getter
@ToString
public class MainViewSearchProductsSessionAttribute {

    private boolean sameCategoryForEachShop;
}
