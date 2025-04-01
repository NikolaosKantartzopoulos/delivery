package nik.delivery.catalogue.api

import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto
import nik.delivery.catalogue.service.FoodCategoryService
import spock.lang.Specification

class FoodCategoryApiSpec extends Specification {

    FoodCategoryApi foodCategoryApi
    FoodCategoryService foodCategoryService = Mock()

    def setup() {
        foodCategoryApi = new FoodCategoryApiImpl(foodCategoryService)
    }

    def "Should delegate createFoodCategory to service layer"() {
        given:
            def displayName = "A display dame"
            CreateFoodCategoryRequestDto requestDto = new CreateFoodCategoryRequestDto(displayName: displayName)
        when:
            foodCategoryApi.createFoodCategory(requestDto)
        then:
            1 * foodCategoryService.createFoodCategory(requestDto)
            0 * _
    }
}
