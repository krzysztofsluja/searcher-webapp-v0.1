package org.sluja.searcher.webapp.mapper.shop;

import org.mapstruct.*;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.model.attribute.ShopAttribute;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShopAttributeMapper {

    @Mapping(source = "shopAttribute.context.name", target = "contextName")
    @Mapping(source = "shopAttribute.shop.name", target = "shopName")
    ShopAttributeDto toDto(ShopAttribute shopAttribute);

}