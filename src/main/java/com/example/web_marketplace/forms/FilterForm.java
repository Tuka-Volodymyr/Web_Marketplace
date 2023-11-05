package com.example.web_marketplace.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterForm {
    private String category;
    private long minPrice;
    private long maxPrice;
}
