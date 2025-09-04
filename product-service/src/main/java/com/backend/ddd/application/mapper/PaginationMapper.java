package com.backend.ddd.application.mapper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


public class PaginationMapper {

    public Pageable convertToPageble(Integer page, Integer pageSize, String sortBy, String sortOrder) {
        // Create Sort object
        Sort sort = this.createSort(sortBy, sortOrder);

        return PageRequest.of(page - 1, pageSize, sort);
    }

    private Sort createSort(String sortBy, String sortOrder) {
        // default sort if sort in empty or null
        if (sortBy == null || sortBy.trim().isEmpty() ||  sortOrder == null || sortOrder.trim().isEmpty()) {
            return Sort.by(Sort.Direction.ASC, "name");
        }
        Sort.Direction direction = "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

        if (sortBy.equalsIgnoreCase("price")) {
            return Sort.by(direction, "price");
        } else {
            return Sort.by(direction, "name");
        }
    }
}
