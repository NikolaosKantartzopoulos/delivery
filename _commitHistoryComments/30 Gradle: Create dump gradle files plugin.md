
# 🛠️ Gradle Utilities Plugin Added

A new `utilities` plugin was introduced to simplify developer diagnostics by generating a snapshot of all `.gradle` files in the project.

---

## ✅ Changes

### 🔧 `build.gradle`
The `utilities` plugin is now applied in the root project:

```groovy
plugins {
    id('utilities')
}
```

### 📁 `buildSrc/src/main/groovy/utilities.gradle`
A new Gradle task `dumpGradleFiles` was added:

```groovy
tasks.register("dumpGradleFiles") {
    group = "diagnostics"
    description = "Collects all *.gradle files under the project directory and writes their paths and contents to ./gradle.details"

    doLast {
        def output = new File("$rootDir/gradle.details")
        output.text = ""
        rootDir.eachFileRecurse { file ->
            if (file.name.endsWith(".gradle") && file.isFile()) {
                output << ">>> ${file.absolutePath - rootDir.absolutePath}\n"
                output << file.text
                output << "\n\n"
            }
        }
    }
}
```

---

## 📄 Usage

Run the following to collect all `.gradle` file paths and contents into a single file:

```bash
./gradlew dumpGradleFiles
```

The output will be written to:

```text
./gradle.details
```

---

Great for sharing project setup or debugging build logic!
