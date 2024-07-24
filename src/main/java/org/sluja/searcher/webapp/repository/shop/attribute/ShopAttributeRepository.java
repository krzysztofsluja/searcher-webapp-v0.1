package org.sluja.searcher.webapp.repository.shop.attribute;

import org.sluja.searcher.webapp.model.attribute.ShopAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ShopAttributeRepository extends JpaRepository<ShopAttribute, Long> {
    @Query("""
            select s from shop_attribute s
            where s.shop.name in ?1 and s.shop.active = true and s.active = true and s.context.name like ?2 and s.context.active = true
            """)
    Optional<List<ShopAttribute>> findAttributesForShopsInContext(final Collection<String> names, final String name);

    @Query("""
            select s from shop_attribute s
            where upper(s.shop.name) like upper(?1) and upper(s.context.name) like upper(?2) and s.active = true and s.shop.active = true and s.context.active = true""")
    Optional<List<ShopAttribute>> findShopAttributesForShopInContext(final String shopName, final String contextName);
}