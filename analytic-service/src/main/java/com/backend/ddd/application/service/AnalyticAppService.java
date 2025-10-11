package com.backend.ddd.application.service;

import com.backend.ddd.controller.model.dto.QuantityByProductDTO;

import java.util.List;

public interface AnalyticAppService {
    List<QuantityByProductDTO> quantityByProduct();
}
