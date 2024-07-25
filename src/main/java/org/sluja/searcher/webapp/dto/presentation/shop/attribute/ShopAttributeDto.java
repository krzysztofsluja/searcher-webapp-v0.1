package org.sluja.searcher.webapp.dto.presentation.shop.attribute;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.attribute.ShopAttribute}
 */
public record ShopAttributeDto(Long id,
                               @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String shopName,
                               @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String contextName,
                               @NotEmpty(message = DtoValidationErrorMessage.SHOP_ATTRIBUTE_NAME_EMPTY) String name,
                               @NotEmpty(message = DtoValidationErrorMessage.SHOP_ATTRIBUTE_VALUE_EMPTY) String value) implements Serializable {

    public String getValueByName(final String name) {
        return this.name.equalsIgnoreCase(name) ? this.value : StringUtils.EMPTY;
    }
}