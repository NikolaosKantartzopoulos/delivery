# 23 Gradle: Set dev as default profile

**Start app using `dev` profile**
```bash
./gradlew bootRun
```

`delivery-application/app-core/build.gradle`
```groovy
tasks.named('bootRun') {
group = 'Development'
description = 'Run the Spring Boot application'
dependsOn 'dockerUp'
--- args = ["--spring.profiles.active=${project.properties['profiles'] ?: 'prod'}"]
+++ args = ["--spring.profiles.active=${project.properties['profiles'] ?: 'dev'}"]
```
