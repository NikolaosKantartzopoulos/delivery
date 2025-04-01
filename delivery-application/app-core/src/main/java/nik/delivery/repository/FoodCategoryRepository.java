package nik.delivery.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import nik.delivery.domain.FoodCategory;

@Repository
public interface FoodCategoryRepository extends CrudRepository<FoodCategory, Long> {
    Optional<FoodCategory> findByDisplayName(String name);
}
