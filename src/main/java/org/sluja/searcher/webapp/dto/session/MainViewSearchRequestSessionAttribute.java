package org.sluja.searcher.webapp.dto.session;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionScope
@Setter
@Getter
@Component
public class MainViewSearchRequestSessionAttribute {

    private final Map<String, List<String>> shopsWithCategories = new HashMap<>();
    private String context;
}
