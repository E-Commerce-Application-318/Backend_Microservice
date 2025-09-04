package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.mapper.PaginationMapper;
import com.backend.ddd.application.mapper.ProductApplicationMapper;
import com.backend.ddd.application.model.ProductRequest;
import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.service.ProductAppService;
import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.domain.model.entity.Product;
import com.backend.ddd.domain.service.ProductDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductAppServiceImpl implements ProductAppService {

    @Autowired
    ProductDomainService productDomainService;

    private final ProductApplicationMapper productApplicationMapper = new ProductApplicationMapper();

    @Override
    public List<ProductResponse> getAllProductsPagaination(Integer page, Integer pageSize, String sortBy, String sortOrder) {
        // Create Pageable object for pagination
        Pageable pageable =  new PaginationMapper().convertToPageble(page, pageSize, sortBy, sortOrder);

        Page<Product> productsPage = productDomainService.getAllProductsPagination(pageable);
        System.out.println("productsPage = " + productsPage.getContent());
        return productApplicationMapper.productListToProductResponseList(productsPage.getContent());
    }

    @Override
    public ProductResponse getProductById(UUID id) {
        Optional<Product> product = productDomainService.getProductById(id);
        if (product.isEmpty()) {
            return null;
        }
        return productApplicationMapper.productToProductResponse(product.get());
    }

    @Override
    public List<ProductResponse> getProductsByShopIdPagination(UUID shopId, Integer page, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageable =  new PaginationMapper().convertToPageble(page, pageSize, sortBy, sortOrder);
        Page<Product> products = productDomainService.getAllProductsByShopIdPagination(shopId, pageable);

        return productApplicationMapper.productListToProductResponseList(products.getContent());
    }

    @Override
    public ProductResponse addProduct(UUID shopId, ProductRequest productRequest) {
        Product product = new Product()
                .setShopId(shopId)
                .setName(productRequest.getName())
                .setDescription(productRequest.getDescription())
                .setBrand(productRequest.getBrand())
                .setPrice(productRequest.getPrice())
                .setStockNumber(productRequest.getStockNumber());
        System.out.println("Product: " + product);
        Product savedProduct = productDomainService.addProduct(product);

        return productApplicationMapper.productToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest productRequest) {
        return null;
    }
}
