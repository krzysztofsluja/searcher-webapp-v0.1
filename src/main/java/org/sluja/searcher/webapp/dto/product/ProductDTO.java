package org.sluja.searcher.webapp.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Builder
public record ProductDTO(@NotEmpty(message = "error.validation.product.name.empty") String name,
                         @NotEmpty(message = "error.validation.shop.name.empty") String shopName,
                         BigDecimal price,
                         List<String> productPageAddress,
                         List<String> imageProductPageAddress,
                         @NotEmpty(message = "error.validation.category.name.empty") String category,
                         @NotEmpty(message = "error.validation.context.empty") String context) implements Serializable {

    public static ProductDTO emptyProductDTO() {
        return ProductDTO.builder()
                .name(StringUtils.EMPTY)
                .shopName(StringUtils.EMPTY)
                .category(StringUtils.EMPTY)
                .price(BigDecimal.ZERO)
                .productPageAddress(Collections.emptyList())
                .imageProductPageAddress(Collections.emptyList())
                .context(StringUtils.EMPTY)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return (((ProductDTO) o).name().equals(this.name()))
                && (((ProductDTO) o).shopName().equals(this.shopName()))
                && (((ProductDTO) o).price().equals(this.price()))
                && (((ProductDTO) o).productPageAddress().equals(this.productPageAddress()))
                && (((ProductDTO) o).imageProductPageAddress().equals(this.imageProductPageAddress()))
                && (((ProductDTO) o).category().equals(this.category())
                && (((ProductDTO) o).context().equals(this.context())));
    }

    public static List<String> NO_IMAGE_ADDRESS() {
        //TODO
        //return List.of(MessageReader.getMessage("attribute.product.image.address"));
        return Collections.emptyList();
    }
}
