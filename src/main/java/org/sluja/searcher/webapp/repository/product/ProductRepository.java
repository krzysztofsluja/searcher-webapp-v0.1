package org.sluja.searcher.webapp.repository.product;

import org.sluja.searcher.webapp.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}