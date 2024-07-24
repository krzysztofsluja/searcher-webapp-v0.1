package org.sluja.searcher.webapp.dto.presentation.shop.list;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.shop.Shop}
 */
public record ShopDto(Long id,
                      String name,
                      String country,
                      String contextName) implements Serializable {
}