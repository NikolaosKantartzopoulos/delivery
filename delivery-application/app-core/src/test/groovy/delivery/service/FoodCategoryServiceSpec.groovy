package delivery.service

import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

import nik.delivery.domain.FoodCategory
import nik.delivery.dto.CreateFoodCategoryRequestDto
import nik.delivery.repository.FoodCategoryRepository
import nik.delivery.service.FoodCategoryService
import spock.lang.Specification

class FoodCategoryServiceSpec extends Specification {

    FoodCategoryService foodCategoryService
    ModelMapper modelMapper = new ModelMapper()
    FoodCategoryRepository foodCategoryRepository = Mock()

    def setup() {
        foodCategoryService = new FoodCategoryService(foodCategoryRepository, modelMapper)
    }

    def "Should successfully create food category"() {
        given:
            def displayName = "A display dame"
            CreateFoodCategoryRequestDto requestDto = new CreateFoodCategoryRequestDto(displayName: displayName)
            def foodCategory = Mock(FoodCategory)
        when:
            foodCategoryService.createFoodCategory(requestDto)
        then:
            1 * foodCategoryRepository.findByDisplayName(requestDto.displayName) >> Optional.empty()
            1 * foodCategoryRepository.save(_) >> foodCategory
            1 * foodCategory.foodCategoryId
            0 * _
    }

    def "Should throw a conflict exception if display name already exists"() {
        given:
            def displayName = "A display dame"
            CreateFoodCategoryRequestDto requestDto = new CreateFoodCategoryRequestDto(displayName: displayName)
            def foodCategory = Mock(FoodCategory)
        when:
            foodCategoryService.createFoodCategory(requestDto)
        then:
            1 * foodCategoryRepository.findByDisplayName(requestDto.displayName) >> Optional.of(foodCategory)
            0 * _
        and:
            def exception = thrown(ResponseStatusException)
            exception.statusCode == HttpStatus.CONFLICT
    }
}
