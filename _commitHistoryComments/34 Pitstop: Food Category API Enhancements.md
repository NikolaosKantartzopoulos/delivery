# Food Category API Enhancements

This commit introduces a set of **preparatory changes** to extend and enhance the `FoodCategory` API within the `catalogue` module of the Delivery Application. It includes DTOs, service logic, REST API extensions, and a switch to JPA for improved flexibility.

---

## ✨ API Changes

- Extended `FoodCategoryApi` interface:
    - `getAllFoodCategories()`
    - `deleteFoodCategory(Long id)`
    - `deleteAllFoodCategories()`

---

## 🆕 New DTOs

- `FoodCategoryDto`: A minimal representation of a food category.
- `FoodCategoryListResponse`: Wrapper object returning a list of `FoodCategoryDto` and a count.

---

## 🚀 Runtime Implementations

- **`FoodCategoryApiImpl`** now implements the new methods for retrieval and deletion.
- **`FoodCategoryRestApi`** updated with:
    - `GET /api/food-category`: Returns all categories
    - `DELETE /api/food-category/{id}`: Deletes by ID
    - `DELETE /api/food-category`: Deletes all categories

---

## 🔧 Service Layer Enhancements

- Added support for:
    - Fetching all food categories
    - Deleting single or all categories
- Includes `capitalizeFirst` utility for consistent `displayName` formatting.
- Handles name conflict during creation with a `409 Conflict`.

---

## 🗃 Repository Change

- Switched from `CrudRepository` to `JpaRepository` in `FoodCategoryRepository` for richer query capabilities.

---

## 🧪 Notes

These changes prepare the system for:
- A complete admin-facing category management UI
- Future validations and role-based access
- Easier integration testing and OpenAPI generation

