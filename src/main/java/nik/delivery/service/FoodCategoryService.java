package nik.delivery.service;

import org.modelmapper.ModelMapper;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import nik.delivery.domain.FoodCategory;
import nik.delivery.dto.CreateFoodCategoryRequestDto;
import nik.delivery.dto.CreateFoodCategoryResponseDto;
import nik.delivery.repository.FoodCategoryRepository;

@Service
public class FoodCategoryService {

    private final FoodCategoryRepository foodCategoryRepository;
    private final ConversionService conversionService;
    private final ModelMapper modelMapper;

    public FoodCategoryService(FoodCategoryRepository foodCategoryRepository, ConversionService conversionService, ModelMapper modelMapper) {
        this.foodCategoryRepository = foodCategoryRepository;
        this.conversionService = conversionService;
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
