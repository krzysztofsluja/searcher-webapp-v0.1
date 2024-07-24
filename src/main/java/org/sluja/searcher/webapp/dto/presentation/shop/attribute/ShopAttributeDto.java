package org.sluja.searcher.webapp.dto.presentation.shop.attribute;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.attribute.ShopAttribute}
 */
public record ShopAttributeDto(Long id,
                               @NotEmpty String shopName,
                               @NotEmpty String contextName,
                               @NotEmpty String name,
                               @NotEmpty String value) implements Serializable {

    public String getValueByName(final String name) {
        return this.name.equalsIgnoreCase(name) ? this.value : StringUtils.EMPTY;
    }
}