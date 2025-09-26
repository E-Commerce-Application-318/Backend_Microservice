package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.ProductAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductAppService productAppService;

    @GetMapping("/all-products")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "pagesize", defaultValue = "10") Integer pageSize,
        @RequestParam(name = "sortby", required = false, defaultValue = "name") String sortBy,
        @RequestParam(name = "sortorder", defaultValue = "asc") String sortOrder

    ) {
        List<ProductResponseDTO> productResponseDTOs = productAppService.getAllProductsPagaination(
            page, pageSize, sortBy, sortOrder);
        if (productResponseDTOs == null || productResponseDTOs.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to fetch all products"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Success fetching all products", productResponseDTOs));
    }

    @GetMapping("/product-detail/{productId}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductDetail(
            @PathVariable("productId") UUID productId
    ) {
        ProductResponseDTO productResponseDTO = productAppService.getProductById(productId);
        if (productResponseDTO == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Product ID not found"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successful fetching product", productResponseDTO));
    }

    @PostMapping("/product-detail-list")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getProductsByProductIds(
        @RequestBody List<UUID> productIds
    ) {
        List<ProductResponseDTO> productResponseDTOs = productAppService.getProductsByProductIds(productIds);
        if (productResponseDTOs == null || productResponseDTOs.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Not found any product!"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successful fetching product", productResponseDTOs));
    }

    @GetMapping("/shop/{shopId}/all-products")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProductsByShopId(
            @PathVariable(value = "shopId") UUID shopId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "pagesize", required = false) Integer pageSize,
            @RequestParam(value = "sortby", required = false) String sortBy,
            @RequestParam(value = "sortorder", required = false) String sortOrder
    ) {
        // handle null value for request params
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 10 : pageSize;
        sortBy = sortBy == null ? "name" : sortBy;
        sortOrder = sortOrder == null ? "asc" : sortOrder;

        List<ProductResponseDTO> productResponseDTOs = productAppService.getProductsByShopIdPagination(shopId, page, pageSize, sortBy, sortOrder);
        if (productResponseDTOs == null || productResponseDTOs.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to fetch all products of this shop"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successfully fetching all products", productResponseDTOs));
    }

    @PostMapping("/shop/{shopId}/add-product")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> addProduct(
            @PathVariable("shopId") UUID shopId,
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        ProductResponseDTO productResponseDTO = productAppService.addProduct(shopId, productRequestDTO);
        if (productResponseDTO == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to add product"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Add product successfully", productResponseDTO));
    }

    @PostMapping("/process-order")
    public ResponseEntity<ApiResponseDTO<Boolean>> processOrder (
            @RequestBody Map<UUID, Integer> productIdsAndQuantities
    ) {
        try {
            boolean result = productAppService.updateProductsProcessOrder(productIdsAndQuantities);
            if (result)
                return ResponseEntity.ok().body(ApiResponseDTO.success("Process order successfully", true));
            else
                return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to process order because stock is not enough"));
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to process order: " + err));
        }
    }

    @GetMapping("/cancel-order")
    public ResponseEntity<ApiResponseDTO<Boolean>> cancelOrder (
            @RequestBody Map<UUID, Integer> productIdsAndQuantities
    ) {
//        try {
//            boolean result = productAppService.updateProductsProcessOrder(productIdsAndQuantities);
//            if (result)
//                return ResponseEntity.ok().body(ApiResponseDTO.success("Process order successfully", true));
//            else
//                return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to process order because stock is not enough"));
//        } catch (Exception err) {
//            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to process order: " + err));
//        }
        return null;
    }
}
