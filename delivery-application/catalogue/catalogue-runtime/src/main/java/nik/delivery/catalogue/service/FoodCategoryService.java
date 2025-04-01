package nik.delivery.catalogue.service;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import nik.delivery.catalogue.domain.FoodCategory;
import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto;
import nik.delivery.catalogue.dto.CreateFoodCategoryResponseDto;
import nik.delivery.catalogue.repository.FoodCategoryRepository;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ModelMapper modelMapper;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository, ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.modelMapper = modelMapper;
    }

    public CreateFoodCategoryResponseDto createFoodCategory(CreateFoodCategoryRequestDto requestDto) {

        if (foodCategoryRepository.findByDisplayName(requestDto.getDisplayName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Food category already exists");
        }

        FoodCategory savedCategory = foodCategoryRepository.save(modelMapper.map(requestDto, FoodCategory.class));

        return new CreateFoodCategoryResponseDto(savedCategory.getFoodCategoryId());
    }
}
