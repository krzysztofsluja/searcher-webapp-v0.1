package org.sluja.searcher.webapp.service.presentation.shop.list;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sluja.searcher.webapp.annotation.validation.InputValidation;
import org.sluja.searcher.webapp.dto.presentation.shop.list.ShopDto;
import org.sluja.searcher.webapp.exception.presentation.SpecificEntityNotFoundException;
import org.sluja.searcher.webapp.exception.presentation.shop.ShopNotFoundException;
import org.sluja.searcher.webapp.mapper.shop.ShopMapper;
import org.sluja.searcher.webapp.model.shop.Shop;
import org.sluja.searcher.webapp.repository.shop.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetShopService {

    private final ShopRepository shopRepository;
    @Autowired
    private final ShopMapper shopMapper;

    public List<ShopDto> getAllShops() {
        //TODO logging
        return shopRepository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(shopMapper::map)
                .toList();
    }

    @InputValidation(inputs = {ShopDto.class})
    public ShopDto getShopByName(final String name) throws SpecificEntityNotFoundException {
        //TODO logging
        return shopRepository
                .findByName(name)
                .map(shopMapper::map)
                .orElseThrow(ShopNotFoundException::new);
    }

    public List<ShopDto> getShopsByContextName(final String contextName) throws SpecificEntityNotFoundException {
        //TODO logging
        final Optional<List<Shop>> shops = shopRepository
                .findByContextName(contextName);
        if(shops.isEmpty()) {
            throw new ShopNotFoundException();
        }
        return shops.get()
                .stream()
                .filter(Objects::nonNull)
                .map(shopMapper::map)
                .toList();
    }
}
