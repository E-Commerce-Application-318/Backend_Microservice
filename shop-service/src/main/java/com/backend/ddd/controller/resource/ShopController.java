package com.backend.ddd.controller.resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop/{shopId}")
public class ShopController {

//    @GetMapping("all-products")
//    public ResponseEntity<ApiResponseDTO<List<Prod>>> getAllProducts(@PathVariable String shopId){}
}
