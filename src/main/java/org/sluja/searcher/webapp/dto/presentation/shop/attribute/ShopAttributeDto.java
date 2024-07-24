package org.sluja.searcher.webapp.dto.presentation.shop.attribute;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * DTO for {@link org.sluja.searcher.webapp.model.attribute.ShopAttribute}
 */
public record ShopAttributeDto(Long id,
                               String shopName,
                               String contextName,
                               String name,
                               String value) implements Serializable {

    public String getValueByName(final String name) {
        return this.name.equalsIgnoreCase(name) ? this.value : StringUtils.EMPTY;
    }
}