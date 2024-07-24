package org.sluja.searcher.webapp.dto.presentation.shop.list;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.shop.Shop}
 */
public record ShopDto(Long id,
                      @NotEmpty String name,
                      @NotEmpty String country,
                      @NotEmpty String contextName) implements Serializable {
}