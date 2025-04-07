
# 22 View: Show active profile-specific properties in home.ftl

This change enhances the `home.ftl` template to show which `application-*.properties` file is being used, based on the active Spring profile.

### Can now start app with dev profile using gradle task bootrun

`delivery-application/app-core/build.gradle`
```groovy
    tasks.named('bootRun') {
        group = 'Development'
        description = 'Run the Spring Boot application'
        dependsOn 'dockerUp'
        args = ["--spring.profiles.active=${project.properties['profiles'] ?: 'prod'}"]
    }
```

**Start app using `dev` profile**
```bash
./gradlew bootRun -Pprofiles=dev
```

---

### ✅ What was added

In `HomepageController.java`:
- Injected `Environment` to read the active Spring profile
- Added the active profile to the model (`activeProfile`)

In `home.ftl`:
- Used FreeMarker conditionals to dynamically render the file name being used:
  
```ftl
<h1>Welcome to ---> ${companyName} <---</h1>
    <#if activeProfile?? && activeProfile != "default">
        <p>Using <code>application-${activeProfile}.properties</code></p>
    <#else>
        <p>Using <code>application.properties</code></p>
    </#if>
```

---

### ▶️ Result

When running with `--spring.profiles.active=dev`, the page will show:

```
Using application-dev.properties
```

When no profile is set:

```
Using application.properties
```

---

### 📁 Files changed
- `delivery-application/app-core/build.gradle`
- `delivery-application/app-core/src/main/java/nik/delivery/web/HomepageController.java`
- `delivery-application/app-core/src/main/resources/application-dev.properties`
- `delivery-application/app-core/src/main/resources/application.properties`
- `delivery-application/app-core/src/main/resources/templates/home.ftl`

