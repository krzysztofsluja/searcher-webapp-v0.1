package org.sluja.searcher.webapp.repository.shop;

import org.sluja.searcher.webapp.model.shop.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    Optional<Shop> findByName(String name);

    @Query("select s from shop s where upper(s.context.name) like upper(?1)")
    Optional<List<Shop>> findByContextName(final String contextName);

}