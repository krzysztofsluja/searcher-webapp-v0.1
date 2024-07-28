package org.sluja.searcher.webapp.dto.presentation.shop.list;

import jakarta.validation.constraints.NotEmpty;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.shop.Shop}
 */
public record ShopDto(Long id,
                      @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String name,
                      @NotEmpty(message = DtoValidationErrorMessage.SHOP_COUNTRY_EMPTY) String country,
                      @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String contextName) implements Serializable {
}