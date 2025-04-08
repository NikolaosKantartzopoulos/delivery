# 25 Gradle: Create versioning plugin to generate Docker image tags

## Summary

Introduced a custom Gradle plugin for generating Docker image tags using versioning and Git metadata. This allows the project to consistently tag Docker images based on `gradle.properties`, the current date, and the latest Git commit hash.

---

## ✅ Changes

### ➕ Added `versioning.gradle` in `buildSrc`:
- Registered a new task `createDockerImageTag` under the `Versioning` group.
- Generates a Docker tag in the format:
  ```
  yyyy-MM-dd-<git-commit>-v<major>.<minor>
  ```
- Stores the tag in `project.ext.generatedDockerTag` for use by other Gradle tasks.

### ➕ Added `gradle.properties` to root:
```properties
majorVersion=1
minorVersion=0
```

### 🔧 Updated root `build.gradle`:
```groovy
id('versioning')
```
Included the new plugin to make the custom task available globally.

---

## 💡 Example Usage

```bash
./gradlew createDockerImageTag
```

Example output:
```text
Docker image tag: 2025-04-08-b629126-v1.0
```

You can access this tag inside other tasks like so:
```groovy
project.generatedDockerTag
```
