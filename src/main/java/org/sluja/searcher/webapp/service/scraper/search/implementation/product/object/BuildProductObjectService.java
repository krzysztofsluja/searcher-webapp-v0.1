package org.sluja.searcher.webapp.service.scraper.search.implementation.product.object;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.sluja.searcher.webapp.dto.product.ProductDTO;
import org.sluja.searcher.webapp.dto.product.request.search.object.BuildProductObjectRequest;
import org.sluja.searcher.webapp.dto.scraper.stat.StaticWebsiteElementScrapRequest;
import org.sluja.searcher.webapp.enums.product.ProductProperty;
import org.sluja.searcher.webapp.exception.format.UnsuccessfulFormatException;
import org.sluja.searcher.webapp.exception.product.general.ProductNotFoundException;
import org.sluja.searcher.webapp.exception.product.object.ProductObjectBuildFailedException;
import org.sluja.searcher.webapp.exception.scraper.ScraperIncorrectFieldException;
import org.sluja.searcher.webapp.service.factory.scraper.WebsiteScraperFactory;
import org.sluja.searcher.webapp.service.scraper.interfaces.WebsiteScraper;
import org.sluja.searcher.webapp.utils.extractor.Extractor;
import org.sluja.searcher.webapp.utils.formatter.ProductFormatter;
import org.sluja.searcher.webapp.utils.logger.LoggerMessageUtils;
import org.sluja.searcher.webapp.utils.logger.LoggerUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Qualifier("buildProductObjectService")
@RequiredArgsConstructor
@Slf4j
public class BuildProductObjectService implements IBuildProductObject {


    private final WebsiteScraperFactory websiteScraperFactory;
    private final Extractor<BigDecimal, Element, BuildProductObjectRequest> productPriceExtractor;
    private final Extractor<List<String>, Element, BuildProductObjectRequest> productPageAddressExtractor;
    private final Extractor<List<String>, Element, BuildProductObjectRequest> productImageAddressExtractor;
    private final LoggerMessageUtils loggerMessageUtils;
    @Override
    public List<ProductDTO> build(final BuildProductObjectRequest request) throws ProductNotFoundException {
        final List<CompletableFuture<ProductDTO>> futureProducts = request.getInstancesToCreateProducts()
                .stream()
                .map(instance -> getProductAsync(instance, request))
                .toList();
        final CompletableFuture<Void> allFutureProducts = CompletableFuture.allOf(
                futureProducts.toArray(new CompletableFuture[0])
        );
        try {
            allFutureProducts.get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                    LoggerUtils.getCurrentMethodName(),
                    e.getMessage()));
            throw new ProductObjectBuildFailedException();
        }
        return futureProducts.stream()
                .map(CompletableFuture::join)
                .filter(product -> !product.equals(ProductDTO.emptyProductDTO()))
                .toList();
    }
    private CompletableFuture<ProductDTO> getProductAsync(final Element instance, final BuildProductObjectRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final CompletableFuture<String> futureName = getName(instance, request);
                final CompletableFuture<List<String>> futureAddresses = getAddresses(instance, request, ProductProperty.URL);
                final CompletableFuture<List<String>> futureImageAddresses = getAddresses(instance, request, ProductProperty.IMAGE_URL);
                final CompletableFuture<BigDecimal> futurePrice = getPrice(instance, request);
                final CompletableFuture futures = CompletableFuture.allOf(
                        futureName,futureAddresses,futurePrice,futureImageAddresses
                );
                futures.get();
                return ProductDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .name(futureName.join())
                        .price(futurePrice.join())
                        .productPageAddress(futureAddresses.join())
                        .imageProductPageAddress(futureImageAddresses.join())
                        .category(request.getCategoryName())
                        .shopName(request.getShopName())
                        .context(request.getContext())
                        .build();
                //TODO add validation for fields
            } catch (Exception e) {
                log.error(loggerMessageUtils.getErrorLogMessageWithDeclaredErrorMessage(LoggerUtils.getCurrentClassName(),
                        LoggerUtils.getCurrentMethodName(),
                        e.getMessage()));
                return ProductDTO.emptyProductDTO();
            }
        });
    }

    private CompletableFuture<String> getName(final Element instance, final BuildProductObjectRequest request) {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                final StaticWebsiteElementScrapRequest scrapRequest = new StaticWebsiteElementScrapRequest(request.getProductName(), instance);
                return ProductFormatter.format(request, ProductProperty.NAME, getScraperService().scrap(scrapRequest));
            } catch (UnsuccessfulFormatException | ScraperIncorrectFieldException e) {
                final String currentClassName = LoggerUtils.getCurrentClassName();
                final String currentMethodName = LoggerUtils.getCurrentMethodName();
                log.error(loggerMessageUtils.getErrorLogMessage(currentClassName,
                        currentMethodName,
                        e.getMessageCode(),
                        e.getErrorCode()));
                log.info(loggerMessageUtils.getInfoLogMessage(currentClassName,
                        currentMethodName,
                        "info.product.object.default.name"));
                return StringUtils.EMPTY;
            }
        });
    }

    private CompletableFuture<BigDecimal> getPrice(final Element element, final BuildProductObjectRequest request) throws ProductNotFoundException {
        return CompletableFuture.supplyAsync(() ->
        {
            try {
                return productPriceExtractor.extract(element, request);
            } catch (final ScraperIncorrectFieldException | ProductNotFoundException | UnsuccessfulFormatException e) {
                final String currentClassName = LoggerUtils.getCurrentClassName();
                final String currentMethodName = LoggerUtils.getCurrentMethodName();
                log.error(loggerMessageUtils.getErrorLogMessage(currentClassName,
                        currentMethodName,
                        e.getMessageCode(),
                        e.getErrorCode()));
                log.info(loggerMessageUtils.getInfoLogMessage(currentClassName,
                        currentMethodName,
                        "info.product.object.default.price"));
                return BigDecimal.ZERO;
            }
        });
    }

    protected CompletableFuture<List<String>> getAddresses(final Element element, final BuildProductObjectRequest request, final ProductProperty property) throws ProductNotFoundException {
        final Extractor<List<String>, Element, BuildProductObjectRequest> extractor = switch (property) {
            case URL -> productPageAddressExtractor;
            case IMAGE_URL -> productImageAddressExtractor;
            default -> null;
        };
        if(Objects.isNull(extractor)) {
            throw new ProductObjectBuildFailedException();
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return extractor.extract(element, request);
            } catch (ProductNotFoundException | UnsuccessfulFormatException | ScraperIncorrectFieldException e) {
                final String currentClassName = LoggerUtils.getCurrentClassName();
                final String currentMethodName = LoggerUtils.getCurrentMethodName();
                log.error(loggerMessageUtils.getErrorLogMessage(currentClassName,
                        currentMethodName,
                        e.getMessageCode(),
                        e.getErrorCode()));
                log.info(loggerMessageUtils.getInfoLogMessage(currentClassName,
                        currentMethodName,
                        "info.product.object.default.address"));
                return Collections.emptyList();
            }
        });
    }

    @Override
    public WebsiteScraper<Element, StaticWebsiteElementScrapRequest> getScraperService() {
        return (WebsiteScraper<Element, StaticWebsiteElementScrapRequest>) websiteScraperFactory.getElementScraper(false);
    }
}
