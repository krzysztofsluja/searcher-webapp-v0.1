package org.sluja.searcher.webapp.dto.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;
import org.sluja.searcher.webapp.utils.dto.validation.DtoValidationErrorMessage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Builder
public record ProductDTO(String id,
                         @NotEmpty(message = DtoValidationErrorMessage.PRODUCT_NAME_EMPTY) String name,
                         @NotEmpty(message = DtoValidationErrorMessage.SHOP_NAME_EMPTY) String shopName,
                         BigDecimal price,
                         List<String> productPageAddress,
                         List<String> imageProductPageAddress,
                         @NotEmpty(message = DtoValidationErrorMessage.CATEGORY_NAME_EMPTY) String category,
                         @NotEmpty(message = DtoValidationErrorMessage.CONTEXT_EMPTY) String context) implements Serializable {

    public static ProductDTO emptyProductDTO() {
        return ProductDTO.builder()
                .id("0L")
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
                && (((ProductDTO) o).id().equals(this.id()))
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
