package org.sluja.searcher.webapp.mapper.shop;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sluja.searcher.webapp.dto.presentation.shop.list.ShopDto;
import org.sluja.searcher.webapp.model.shop.Shop;

@Mapper(componentModel = "spring")
public interface ShopMapper {

    @Mapping(target = "contextName", source = "shop.context.name")
    @Valid ShopDto map(final Shop shop);
}
