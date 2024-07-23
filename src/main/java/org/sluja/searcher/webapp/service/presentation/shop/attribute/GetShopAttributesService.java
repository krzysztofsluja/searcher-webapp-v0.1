package org.sluja.searcher.webapp.service.presentation.shop.attribute;

import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.dto.presentation.shop.attribute.ShopAttributeDto;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.attribute.AttributeForGivenShopInContextNotFoundException;
import org.sluja.searcher.webapp.mapper.shop.ShopAttributeMapper;
import org.sluja.searcher.webapp.repository.shop.attribute.ShopAttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetShopAttributesService {

    private final ShopAttributeRepository shopAttributeRepository;
    @Autowired
    private final ShopAttributeMapper shopAttributeMapper;

    public List<ShopAttributeDto> getAttributesForGivenShopAndContext(final String shopName, final String context) throws SpecificEntityNotFoundException {
        return shopAttributeRepository
                .findShopAttributesForShopInContext(shopName, context)
                .orElseThrow(AttributeForGivenShopInContextNotFoundException::new)
                .stream()
                .map(shopAttributeMapper::toDto)
                .toList();
    }

    public Map<String, List<ShopAttributeDto>> getAttributesForManyShopsInContext(final List<String> shopNames, final String context) throws SpecificEntityNotFoundException {
        return shopAttributeRepository
                .findAttributesForShopsInContext(shopNames, context)
                .orElseThrow(AttributeForGivenShopInContextNotFoundException::new)
                .stream()
                .map(shopAttributeMapper::toDto)
                .collect(Collectors.groupingBy(ShopAttributeDto::shopName));
    }
}
