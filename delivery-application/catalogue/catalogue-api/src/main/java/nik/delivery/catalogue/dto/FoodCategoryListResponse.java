package nik.delivery.catalogue.dto;

import java.util.List;

import lombok.Data;

@Data
public class FoodCategoryListResponse {
    private List<FoodCategoryDto> items;
    private int count;
}
