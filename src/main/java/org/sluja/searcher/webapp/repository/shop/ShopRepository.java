package org.sluja.searcher.webapp.repository.shop;

import org.sluja.searcher.webapp.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}