package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.service.AnalyticAppService;
import com.backend.ddd.controller.model.dto.QuantityByProductDTO;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnalyticAppServiceImpl implements AnalyticAppService {

    @Autowired
    private InteractiveQueryService interactiveQueryService;

    @Value("${windowstore.name}")
    private String WINDOWSTORE_NAME;
    @Value("${windowstore.size.ms}")
    private long WINDOW_SIZE_MS;

    @Override
    public List<QuantityByProductDTO> quantityByProduct() {
        List<QuantityByProductDTO> windowedQuantityByProduct = new ArrayList<>();
        long now = Instant.now().toEpochMilli();

        // calculate the time range for most recently completeted window
        long targetWindowStartTime = (now / WINDOW_SIZE_MS) * WINDOW_SIZE_MS - WINDOW_SIZE_MS;
        Instant timeFrom = Instant.ofEpochMilli(targetWindowStartTime);
        Instant timeTo = timeFrom.plus(Duration.ofMillis(WINDOW_SIZE_MS));

        try (KeyValueIterator<Windowed<String>, Integer> all = getProductQuantityStore().fetchAll(timeFrom, timeTo)) {
            while (all.hasNext()) {
                KeyValue<Windowed<String>, Integer> kv = all.next();
                QuantityByProductDTO quantityByProduct = new QuantityByProductDTO();
                quantityByProduct.setProductId(kv.key.key());
                quantityByProduct.setQuantity(kv.value);
                quantityByProduct.setWindowStart(kv.key.window().startTime());
                quantityByProduct.setWindowEnd(kv.key.window().endTime());
                windowedQuantityByProduct.add(quantityByProduct);
            }
        }
        return windowedQuantityByProduct;
    }

    private ReadOnlyWindowStore<String, Integer> getProductQuantityStore() {
        return this.interactiveQueryService.getQueryableStore(WINDOWSTORE_NAME, QueryableStoreTypes.windowStore());
    }
}
