package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.AnalyticAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.QuantityByProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytic")
public class AnalyticController {

    @Autowired
    private AnalyticAppService analyticAppService;

    @GetMapping("/quantity-by-product")
    public ResponseEntity<ApiResponseDTO<List<QuantityByProductDTO>>> quantityByProduct() {
        List<QuantityByProductDTO> quantityByProducts = analyticAppService.quantityByProduct();
        if (quantityByProducts.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Error when get analytics."));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successfully get analytic Quantity by Product", quantityByProducts));
    }
}
