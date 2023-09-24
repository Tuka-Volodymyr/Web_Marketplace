package com.example.web_marketplace.dto;

import lombok.Data;

@Data
public class FilterForm {
    private String category;
    private long minPrice;
    private long maxPrice;
}
