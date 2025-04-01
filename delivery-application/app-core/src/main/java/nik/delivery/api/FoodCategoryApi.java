package nik.delivery.api;

import org.springframework.stereotype.Component;

import nik.delivery.dto.CreateFoodCategoryRequestDto;
import nik.delivery.dto.CreateFoodCategoryResponseDto;
import nik.delivery.service.FoodCategoryService;

@Component
public class FoodCategoryApi {

    private final FoodCategoryService foodCategoryService;

    public FoodCategoryApi(FoodCategoryService foodCategoryService) {
        this.foodCategoryService = foodCategoryService;
    }


    public CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto) {
        return foodCategoryService.createFoodCategory(requestDto);
    }
}
