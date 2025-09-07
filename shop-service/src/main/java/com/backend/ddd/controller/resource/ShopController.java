package com.backend.ddd.controller.resource;

import com.backend.ddd.application.model.ProductResponse;
import com.backend.ddd.application.model.ShopProductsResponse;
import com.backend.ddd.application.model.ShopResponse;
import com.backend.ddd.application.service.ShopAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.ShopProductsResponseDTO;
import com.backend.ddd.controller.model.dto.ShopResponseDTO;
import com.backend.ddd.controller.model.mapper.ShopControllerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shop/{shopId}")
public class ShopController {

    @Autowired
    private ShopAppService shopAppService;

    private final ShopControllerMapper shopControllerMapper = new ShopControllerMapper();

    @GetMapping("/shop-detail")
    public ResponseEntity<ApiResponseDTO<ShopResponseDTO>> getShopDetail(
            @PathVariable("shopId") UUID shopId
    ) {
        ShopResponse shopResponse = shopAppService.getShopDetailById(shopId);
        if (shopResponse == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Shop Not Found"));
        }
        ShopResponseDTO shopResponseDTO = shopControllerMapper.shopResponseToShopResponseDTO(shopResponse);
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successful load shop detail", shopResponseDTO));
    }

    @GetMapping("/shop-detail/products")
    // In real project please convert into DTOs before return for client
    public ResponseEntity<ApiResponseDTO<ShopProductsResponseDTO>> getShopDetailProducts(
        @PathVariable("shopId") UUID shopId
    ) {
        ShopResponse shopResponse = shopAppService.getShopDetailById(shopId);
        List<ProductResponse> productResponseList = shopAppService.getProductsByShopId(shopId);
        if (shopResponse == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Shop Not Found"));
        }
        if (productResponseList == null) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("No product in this shop found"));
        }
        ShopProductsResponseDTO shopProductsResponseDTO = new ShopProductsResponseDTO()
                .setShopResponse(shopResponse)
                .setProductResponses(productResponseList);
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successful load shop detail and products", shopProductsResponseDTO));
    }
//    @GetMapping("all-products")
//    public ResponseEntity<ApiResponseDTO<List<Prod>>> getAllProducts(@PathVariable String shopId){
//
//    }
}
