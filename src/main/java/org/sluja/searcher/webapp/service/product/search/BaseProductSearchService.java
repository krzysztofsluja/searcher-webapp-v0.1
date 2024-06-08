package org.sluja.searcher.webapp.service.product.search;

import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.scraper.ProductScrapWithDefinedAttributes;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.connection.ConnectionTimeoutException;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.TooManyProductPagesException;
import org.sluja.searcher.webapp.service.scraper.product.ProductScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class BaseProductSearchService {

    private Logger logger = Logger.getLogger(BaseProductSearchService.class);
    private ProductScraper productScraperService;
    private Extractor<BigDecimal, Element, ProductScrapWithDefinedAttributes> productPriceExtractor;
    private Extractor<List<String>, Element, ProductScrapWithDefinedAttributes> productPageAddressExtractor;
    private Extractor<List<String>, Element, ProductScrapWithDefinedAttributes> productImageAddressExtractor;

    protected List<ProductDTO> searchProductsForCategory(final String category, final ProductScrapWithDefinedAttributes request) throws ConnectionTimeoutException, UnsuccessfulFormatException, ProductNotFoundException, IOException, TooManyProductPagesException {
        final List<String> categoriesAddresses = findCategoriesAddresses(request);
        final List<String> categoryAddresses = new ArrayList<>();
        final List<String> allCategoryProperties = request.categoryProperties().get(category);
        for(final String property : allCategoryProperties) {
            try {
                final List<String> address = findPageAddressesForCategory(categoriesAddresses, property, request);
                if (CollectionUtils.isNotEmpty(address) || categoryAddresses.contains(address)) {
                    categoryAddresses.addAll(address);
                }
            } catch (TooManyProductPagesException | ProductNotFoundException e) {
                logger.info(String.format("Error during getting page address of given category %s and property %s", category, property));
                if(CollectionUtils.isEmpty(categoryAddresses) && allCategoryProperties.indexOf(property) == allCategoryProperties.size() - 1) {
                    logger.info("No category address found - error throwing");
                    throw e;
                }
            }
        }
        final Elements page = productScraperService.scrapElements(request, categoryAddresses);
        return getProducts(page, category, request);
    }

    protected List<ProductDTO> searchProductsForCategoryOrReturnEmptyListOnError(final String category, final ProductScrapWithDefinedAttributes request) {
        try {
            return searchProductsForCategory(category, request);
        } catch (Exception e) {
            logger.error("Error during product search occurred", e);
            return Collections.emptyList();
        }
    }

    protected List<ProductDTO> getProducts(final Elements scrapedPage, final String category, final ProductScrapWithDefinedAttributes request) throws UnsuccessfulFormatException, ProductNotFoundException {
        final List<CompletableFuture<ProductDTO>> futureProducts = getProductsAsync(scrapedPage, category, request);
        CompletableFuture<Void> allFutureProducts = CompletableFuture.allOf(
                futureProducts.toArray(new CompletableFuture[0])
        );
        try {
            allFutureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return futureProducts.stream()
                .map(CompletableFuture::join)
                .filter(product -> !product.equals(ProductDTO.emptyProductDTO()))
                .toList();
    }

    protected List<CompletableFuture<ProductDTO>> getProductsAsync(final Elements scrapedPage, final String category, final ProductScrapWithDefinedAttributes request) {
        return scrapedPage.stream()
                .map(element -> buildProductAsync(element, request, category))
                .toList();
    }

    protected CompletableFuture<ProductDTO> buildProductAsync(final Element element, final ProductScrapWithDefinedAttributes request, final String category) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final CompletableFuture<String> futureName = getName(element, request);
                final CompletableFuture<List<String>> futureAddresses = getAddresses(element, request, ProductProperty.URL);
                final CompletableFuture<List<String>> futureImageAddresses = getAddresses(element, request, ProductProperty.IMAGE_URL);
                final CompletableFuture<BigDecimal> futurePrice = getPrice(element, request);
                final CompletableFuture<String> futureDescription = futureAddresses.thenApplyAsync(address -> getDescription(address, request));
                final CompletableFuture futures = CompletableFuture.allOf(
                        futureName,futureAddresses,futurePrice,futureImageAddresses,futureDescription
                );
                futures.get();
                return ProductDTO.builder()
                        .name(futureName.join())
                        .price(futurePrice.join())
                        .productPageAddress(futureAddresses.join())
                        .imageProductPageAddress(futureImageAddresses.join())
                        .category(category)
                        .shopName(request.shopName())
                        .description(futureDescription.join())
                        .build();
            } catch (Exception e) {
                logger.error("Error during product search occurred", e);
                return ProductDTO.emptyProductDTO();
            }
        });
    }

    protected CompletableFuture<String> getName(final Element element, final ProductScrapWithDefinedAttributes request) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return ProductFormatter.format(request, ProductProperty.NAME, productScraperService.scrapElementByAttribute(element, request.productName()));
            } catch (UnsuccessfulFormatException e) {
                logger.info("Error during formatting product name");
                return StringUtils.EMPTY;
            }
        });
    }

    protected CompletableFuture<BigDecimal> getPrice(final Element element, final ProductScrapWithDefinedAttributes request) throws ProductNotFoundException {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return productPriceExtractor.extract(element, request);
            } catch (ProductNotFoundException | UnsuccessfulFormatException e) {
                logger.info("Error during formatting product price");
                return BigDecimal.ZERO;
            }
        });
    }

    protected CompletableFuture<List<String>> getAddresses(final Element element, final ProductScrapWithDefinedAttributes request, final ProductProperty property) throws ProductNotFoundException {
        final Extractor extractor = switch (property) {
            case URL -> productPageAddressExtractor;
            case IMAGE_URL -> productImageAddressExtractor;
            default -> null;
        };
        if(extractor == null) {
            throw new ProductNotFoundException();
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return (List<String>)extractor.extract(element, request);
            } catch (ProductNotFoundException | UnsuccessfulFormatException e) {
                logger.info("Error during formatting product address");
                return Collections.emptyList();
            }
        });
    }

    protected List<String> findCategoriesAddresses(final ProductScrapWithDefinedAttributes request) throws ConnectionTimeoutException, IOException {
        final List<String> addresses = new ArrayList<>();
        for (String property : request.allCategoriesPageAddresses()) {
            final Elements elements = productScraperService.scrapElementsFromPage(request.homePageAddress(), property);
            List<String> insertAddress = new ArrayList<>(elements.stream()
                    .map(element -> element.attr(request.pageAddressExtractAttribute()))
                    .map(element -> element.replaceAll("\\s", StringUtils.EMPTY))
                    .distinct()
                    .toList());
            addresses.addAll(insertAddress);
        }
        return addresses;
    }

    protected List<String>  findPageAddressesForCategory(final List<String> pages, final String category, final ProductScrapWithDefinedAttributes request) throws TooManyProductPagesException, ConnectionTimeoutException, IOException, ProductNotFoundException {
        final List<String> categoryPages = pages.stream()
                .filter(page -> isPageContainingCategory(page, category))
                .toList();
        if (categoryPages.size() > 1) {
            throw new TooManyProductPagesException();
        }
        if(categoryPages.isEmpty()) {
            throw new ProductNotFoundException();
        }

        final List<String> productAddresses = new ArrayList<>();
        final String pageAddress = ProductFormatter.formatIncompleteUri(categoryPages.get(0), request);
        for (final String property : request.categoryPageAmounts()) {
            Elements pageElements = productScraperService.scrapElementsFromPage(pageAddress, property);
            List<String> addresses = pageElements.stream()
                    .map(pageUrlElement -> pageUrlElement.attr(request.productPageAddressAttribute()))
                    .map(pageUrlElement -> pageUrlElement.replaceAll("\\s", ""))
                    .distinct()
                    .collect(Collectors.toList());

            if (addresses.isEmpty()) {
                addresses.add(pageAddress);
            }
            if (addresses.get(0).isBlank()) {
                addresses.set(0, pageAddress);
            }
            productAddresses.addAll(addresses);
        }
        if (CollectionUtils.isEmpty(productAddresses)) {
            return List.of(pageAddress);
        }
        return productAddresses;
    }

    public String getDescription(final List<String> productPageAddresses, final ProductScrapWithDefinedAttributes request) {
        if (CollectionUtils.isEmpty(productPageAddresses)) {
            return StringUtils.EMPTY;
        }
        return productPageAddresses.stream()
                .map(address -> {
                    try {
                        return productScraperService.scrapElementsFromPage(address, request.productDescription()).text();
                    } catch (ConnectionTimeoutException | IOException _) {
                        return StringUtils.EMPTY;
                    }
                })
                .collect(Collectors.joining());
    }

    protected boolean isPageContainingCategory(@NotEmpty final String page, @NotEmpty final String category) {
        return page.contains(category);
    }

    protected boolean isPageContainingCategory(final String page, final List<String> categories) {
        for(final String name : categories) {
            if(isPageContainingCategory(page, name)) {
                return true;
            }
        }
        return false;
    }
}
