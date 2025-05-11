package nik.delivery.catalogue.api

import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.simple.SimpleMeterRegistry
import nik.delivery.catalogue.dto.CreateFoodCategoryRequestDto
import spock.lang.Specification

class FoodCategoryRestApiSpec extends Specification {

    FoodCategoryRestApi foodCategoryRestApi
    FoodCategoryApiImpl foodCategoryApi = Mock()
    MeterRegistry meterRegistry = new SimpleMeterRegistry()

    def setup() {
        foodCategoryRestApi = new FoodCategoryRestApi(foodCategoryApi, meterRegistry)
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
