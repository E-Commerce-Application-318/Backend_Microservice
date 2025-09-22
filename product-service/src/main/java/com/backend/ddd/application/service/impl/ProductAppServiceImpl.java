package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.PaginationMapper;
import com.backend.ddd.application.mapper.ProductApplicationMapper;
import com.backend.ddd.application.service.ProductAppService;
import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Product;
import com.backend.ddd.domain.service.ProductDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ProductAppServiceImpl implements ProductAppService {

    @Autowired
    ProductDomainService productDomainService;

    private final ProductApplicationMapper productApplicationMapper = new ProductApplicationMapper();

    @Override
    public List<ProductResponseDTO> getAllProductsPagaination(Integer page, Integer pageSize, String sortBy, String sortOrder) {
        // Create Pageable object for pagination
        Pageable pageable =  new PaginationMapper().convertToPageble(page, pageSize, sortBy, sortOrder);

        Page<Product> productsPage = productDomainService.getAllProductsPagination(pageable);
        return productApplicationMapper.productsToProductResponseDTOs(productsPage.getContent());
    }

    @Override
    public ProductResponseDTO getProductById(UUID id) {
        Optional<Product> product = productDomainService.getProductById(id);
        if (product.isEmpty()) {
            return null;
        }
        return productApplicationMapper.productToProductResponseDTO(product.get());
    }

    @Override
    public List<ProductResponseDTO> getProductsByProductIds(List<UUID> productIds) {
        List<Product> products = productDomainService.getProductsByProductIds(productIds);
        if (products.isEmpty()) {
            return List.of();
        }
        return productApplicationMapper.productsToProductResponseDTOs(products);
    }

    @Override
    public List<ProductResponseDTO> getProductsByShopIdPagination(UUID shopId, Integer page, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable =  new PaginationMapper().convertToPageble(page, pageSize, sortBy, sortOrder);
        Page<Product> products = productDomainService.getAllProductsByShopIdPagination(shopId, pageable);
        return productApplicationMapper.productsToProductResponseDTOs(products.getContent());
    }

    @Override
    public ProductResponseDTO addProduct(UUID shopId, ProductRequestDTO productRequestDTO) {
        Product product = new Product()
                .setShopId(shopId)
                .setName(productRequestDTO.getName())
                .setDescription(productRequestDTO.getDescription())
                .setBrand(productRequestDTO.getBrand())
                .setPrice(productRequestDTO.getPrice())
                .setStockNumber(productRequestDTO.getStockNumber());
        Product savedProduct = productDomainService.addProduct(product);

        return productApplicationMapper.productToProductResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(ProductRequestDTO productRequestDTO) {
        return null;
    }

    @Override
    public Boolean updateProductsProcessOrder(Map<UUID, Integer> productIdAndQuantityMap) {
        List<Product> products = productDomainService.getProductsByProductIds(productIdAndQuantityMap.keySet().stream().toList());
        for (Product product : products) {
            if (product.getStockNumber() < productIdAndQuantityMap.get(product.getId())) {
               return false;
            }
        }

        for (Product product : products) {
            // reduce the stock number of product in database
            product.setStockNumber(product.getStockNumber() - productIdAndQuantityMap.get(product.getId()));
        }
        List<Product> productResponses =  productDomainService.updateProducts(products);
        log.info("ProductResponses: {}", productResponses);
        return !productResponses.isEmpty(); // means success if the updated list is not empty
    }
}
