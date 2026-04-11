package com.darcode.curalink.dto.shared;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PaginatedResponse<T> {

    private final List<T> content;
    private final int totalElements;
    private final int totalPages;
    private final int currentPage;

    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = (int) page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.currentPage = page.getNumber();
    }
}