package nik.delivery.catalogue.api

import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto
import spock.lang.Specification

class FoodCategoryRestApiSpec extends Specification {

    FoodCategoryRestApi foodCategoryRestApi
    FoodCategoryApi foodCategoryApi = Mock()

    def setup() {
        foodCategoryRestApi = new FoodCategoryRestApi(foodCategoryApi)
    }

    def "Should delegate createFoodCategory to nik.delivery.api layer"() {
        given:
            def displayName = "A display dame"
            CreateFoodCategoryRequestDto requestDto = new CreateFoodCategoryRequestDto(displayName: displayName)
        when:
            foodCategoryRestApi.createFoodCategory(requestDto)
        then:
            1 * foodCategoryApi.createFoodCategory(requestDto)
            0 * _
    }
}
