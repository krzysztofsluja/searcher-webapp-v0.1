package org.sluja.searcher.webapp.dto.presentation.shop.list;

import jakarta.validation.constraints.NotEmpty;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.shop.Shop}
 */
public record ShopDto(Long id,
                      @NotEmpty(message = "error.validation.shop.name.empty") String name,
                      @NotEmpty(message = "error.validation.shop.country.empty") String country,
                      @NotEmpty(message = "error.validation.context.empty") String contextName) implements Serializable {
}