package com.backend.ddd.controller.resource;

import com.backend.ddd.application.service.AnalyticAppService;
import com.backend.ddd.controller.model.dto.ApiResponseDTO;
import com.backend.ddd.controller.model.dto.BrandAnalyticDTO;
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

    @GetMapping("/product-analytic")
    public ResponseEntity<ApiResponseDTO<List<QuantityByProductDTO>>> productAnalytic() {
        List<QuantityByProductDTO> quantityByProducts = analyticAppService.quantityByProduct();
        if (quantityByProducts.isEmpty()) {
            return ResponseEntity.badRequest().body(ApiResponseDTO.error("Error when get analytics."));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successfully get analytic Quantity by Product", quantityByProducts));
    }

    @GetMapping("/brand-analytic")
    public ResponseEntity<ApiResponseDTO<List<BrandAnalyticDTO>>> brandAnalytic() {
        List<BrandAnalyticDTO> brandAnalyticDTOs = analyticAppService.brandAnalyticProcess();
        if (brandAnalyticDTOs.isEmpty()) {
            return ResponseEntity.ok().body(ApiResponseDTO.success("Failed to get analytics, data is null", null));
        }
        return ResponseEntity.ok().body(ApiResponseDTO.success("Successfully get analytic Quantity by Product", brandAnalyticDTOs));
    }
}
