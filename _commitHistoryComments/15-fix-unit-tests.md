# 14-fix-unit-tests-location-and-configuration.md

## ✅ Summary

This commit fixes the setup and execution of unit tests across the `app-core` and `catalogue-runtime` modules. Tests were not being picked up or executed due to incorrect file placement and missing Gradle test configuration.

---

## 🛠️ Key Fixes

### 1. Moved Test Class to the Correct Directory

- ✅ Renamed:
  ```
  from: app-core/src/test/java/nik/delivery/DeliveryApplicationTests.java
  to:   app-core/src/main/test/java/nik/delivery/DeliveryApplicationTests.java
  ```

- 📌 **Reason**: The `test` source set must reside under `src/test/java`, not `src/main/test/java`, for Gradle to detect and run tests.

### 2. Fixed Mock Declaration

- 🔁 Updated `FoodCategoryRestApiSpec.groovy`:
  ```groovy
  - FoodCategoryApiImpl foodCategoryApi = Mock()
  + FoodCategoryApi foodCategoryApi = Mock()
  ```

- 📌 **Reason**: The mock type must match the interface (`FoodCategoryApi`) used in the constructor, not the concrete class (`FoodCategoryApiImpl`).

### 3. Cleaned Up Gradle Test Config in `catalogue-runtime`

- 🧹 Removed duplicated JUnit dependencies:
  ```groovy
  testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  ```

- ✅ Left only Spock, since it's used across the module.

### 4. Removed Explicit `useJUnitPlatform()`

- Spock works out of the box with the `groovy` plugin and doesn't require JUnit platform to be explicitly enabled in this module.

---

## 🧪 Next Steps

- You can now run all tests using:
  ```bash
  ./gradlew test
  ```

- To run specific tests:
  ```bash
  ./gradlew :delivery-application:catalogue:catalogue-runtime:test --tests '*FoodCategoryRestApiSpec'
  ```
