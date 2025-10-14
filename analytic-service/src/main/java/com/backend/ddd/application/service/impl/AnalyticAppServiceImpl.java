package com.backend.ddd.application.service.impl;

import com.backend.ddd.application.service.AnalyticAppService;
import com.backend.ddd.controller.model.dto.BrandAnalyticDTO;
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

        try (KeyValueIterator<Windowed<String>, Integer> all = getProductAnalytic().fetchAll(timeFrom, timeTo)) {
            while (all.hasNext()) {
                KeyValue<Windowed<String>, Integer> kv = all.next();
                QuantityByProductDTO quantityByProduct = new QuantityByProductDTO(
                    kv.key.key(),
                    kv.value,
                    kv.key.window().startTime(),
                    kv.key.window().endTime()
                );
                windowedQuantityByProduct.add(quantityByProduct);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return windowedQuantityByProduct;
    }

    @Override
    public List<BrandAnalyticDTO> brandAnalyticProcess() {
        List<BrandAnalyticDTO> windowedBrandAnalyticDTOs = new ArrayList<>();
        long now = Instant.now().toEpochMilli();

        // calculate the time range for most recently completeted window
        long targetWindowStartTime = (now / WINDOW_SIZE_MS) * WINDOW_SIZE_MS - WINDOW_SIZE_MS;
        Instant timeFrom = Instant.ofEpochMilli(targetWindowStartTime);
        Instant timeTo = timeFrom.plus(Duration.ofMillis(WINDOW_SIZE_MS));
        try (KeyValueIterator<Windowed<String>, String> all = getBrandAnalytic().fetchAll(timeFrom, timeTo)) {
            while (all.hasNext()) {
                KeyValue<Windowed<String>, String> kv = all.next();
                BrandAnalyticDTO brandAnalyticDTO = new BrandAnalyticDTO(
                        kv.key.key(),
                        kv.value,
                        kv.key.window().startTime(),
                        kv.key.window().endTime()
                );
                windowedBrandAnalyticDTOs.add(brandAnalyticDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return windowedBrandAnalyticDTOs;
    }

    private ReadOnlyWindowStore<String, Integer> getProductAnalytic() {
        return this.interactiveQueryService.getQueryableStore(WINDOWSTORE_NAME + "-product", QueryableStoreTypes.windowStore());
    }

    private ReadOnlyWindowStore<String, String> getBrandAnalytic() {
        return this.interactiveQueryService.getQueryableStore(WINDOWSTORE_NAME + "-brand", QueryableStoreTypes.windowStore());
    }
}
