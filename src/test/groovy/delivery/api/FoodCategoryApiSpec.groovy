package delivery.api

import nik.delivery.api.FoodCategoryApi
import nik.delivery.api.FoodCategoryRestApi
import nik.delivery.dto.CreateFoodCategoryRequestDto
import nik.delivery.service.FoodCategoryService
import spock.lang.Specification

class FoodCategoryApiSpec extends Specification {

    FoodCategoryApi foodCategoryApi
    FoodCategoryService foodCategoryService = Mock()

    def setup() {
        foodCategoryApi = new FoodCategoryApi(foodCategoryService)
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