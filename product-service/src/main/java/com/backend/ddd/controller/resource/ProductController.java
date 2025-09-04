package com.backend.ddd.controller.resource;

import com.backend.ddd.application.model.ProductRequest;
import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.service.ProductAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.ProductRequestDTO;
import com.backend.ddd.controller.model.dto.ProductResponseDTO;
import com.backend.ddd.controller.model.mapper.ProductControllerMapper;
import com.backend.ddd.domain.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductAppService productAppService;

    private final ProductControllerMapper productControllerMapper = new ProductControllerMapper();

    @GetMapping("/all-products")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProducts(
        @RequestParam(name = "page", defaultValue = "1") Integer page,
        @RequestParam(name = "pagesize", defaultValue = "10") Integer pageSize,
        @RequestParam(name = "sortby", required = false, defaultValue = "name") String sortBy,
        @RequestParam(name = "sortorder", defaultValue = "asc") String sortOrder

    ) {
        List<ProductResponse> productResponseList = productAppService.getAllProductsPagaination(
            page, pageSize, sortBy, sortOrder);
        List<ProductResponseDTO> productResponseDTOList = productControllerMapper.productResponseListToProductReponseDTOList(productResponseList);

        if (productResponseDTOList == null || productResponseDTOList.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to fetch all products"));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Success fetching all products", productResponseDTOList));
    }

    @GetMapping("/product-detail/{productId}")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> getProductDetail(
            @PathVariable("productId") UUID productId
    ) {
        ProductResponse productReponse = productAppService.getProductById(productId);
        if (productReponse == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Product ID not found"));
        }
        ProductResponseDTO productResponseDTO = productControllerMapper.productResponseToProductResponseDTO(productReponse);
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successful fetching product", productResponseDTO));
    }

    @GetMapping("/shop/{shopId}/all-products")
    public ResponseEntity<ApiResponseDTO<List<ProductResponseDTO>>> getAllProductsByShopId(
            @PathVariable("shopId") UUID shopId,
            @RequestParam("page") Integer page,
            @RequestParam("pagesize") Integer pageSize,
            @RequestParam("sortby") String sortBy,
            @RequestParam("sortorder") String sortOrder
    ) {
        List<ProductResponse> productResponseList = productAppService.getProductsByShopIdPagination(shopId, page, pageSize, sortBy, sortOrder);
        if (productResponseList == null || productResponseList.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to fetch all products of this shop"));
        }
        List<ProductResponseDTO> productResponseDTOList = productControllerMapper.productResponseListToProductReponseDTOList(productResponseList);

        return ResponseEntity.ok().body(ApiResponseDTO.success("Successfully fetching all products", productResponseDTOList));
    }

    @PostMapping("/shop/{shopId}/add-product")
    public ResponseEntity<ApiResponseDTO<ProductResponseDTO>> addProduct(
            @PathVariable("shopId") UUID shopId,
            @RequestBody ProductRequestDTO productRequestDTO
    ) {
        ProductRequest productRequest =  productControllerMapper.productRequestDTOToProductRequest(productRequestDTO);
        System.out.println("Product controller mapper: " + productRequest);
        ProductResponse productResponse = productAppService.addProduct(shopId, productRequest);
        System.out.println("Product controller mapper: " + productResponse);
        if (productResponse == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Failed to add product"));
        }

        ProductResponseDTO productResponseDTO = productControllerMapper.productResponseToProductResponseDTO(productResponse);
        return ResponseEntity.ok().body(ApiResponseDTO.success("Add product successfully", productResponseDTO));
    }
}
