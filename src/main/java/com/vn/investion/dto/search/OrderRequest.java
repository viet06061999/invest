package com.vn.investion.dto.search;

import org.springframework.data.domain.Sort;

public class OrderRequest {
    private Sort.Direction direction;
    private String property;

    public OrderRequest() {
    }

    public OrderRequest(Sort.Direction direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }

    public Sort.Order toOrder() {
        return new Sort.Order(direction, property);
    }
}
