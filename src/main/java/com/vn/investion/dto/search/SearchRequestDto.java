package com.vn.investion.dto.search;

import org.springframework.data.domain.Sort;

public class SearchRequestDto {
    private int page;
    private int perPage;
    private SearchCriteria search;
    private OrderRequest order;

    public SearchRequestDto() {
    }

    public SearchRequestDto(int page, int perPage, SearchCriteria search, OrderRequest order) {
        this.page = page;
        this.perPage = perPage;
        this.search = search;
        this.order = order;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public SearchCriteria getSearch() {
        return search;
    }

    public void setSearch(SearchCriteria search) {
        this.search = search;
    }

    public Sort.Order getOrder() {
        return order.toOrder();
    }

    public void setOrder(OrderRequest order) {
        this.order = order;
    }
}
