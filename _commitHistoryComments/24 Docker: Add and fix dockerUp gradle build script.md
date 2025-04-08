# 24 Improve Docker Compose Task for Gradle Integration

## Summary

Enhanced the `dockerUp` Gradle task to ensure it correctly executes `docker compose up -d` from the project root and avoids environment-related execution errors by using the full path to the Docker binary.

## Changes

- Updated `dockerUp` task:
    - Set `workingDir` to `rootDir` to make sure it picks up the root-level `docker-compose.yml`.
    - Explicitly used `/usr/local/bin/docker` instead of `docker` to avoid "command not found" issues on some environments.

- Made `test` task depend on `dockerUp`:
    - Ensures Docker services (e.g. PostgreSQL) are up before integration tests are run.

## Example

```groovy
tasks.register('dockerUp', Exec) {
    group = 'infrastructure'
    description = 'Runs docker compose up -d'
    workingDir rootDir
    commandLine '/usr/local/bin/docker', 'compose', 'up', '-d'
}

tasks.named('test') {
    group = 'Development'
    description = 'Run all tests using Spock'
    dependsOn 'dockerUp'
}
```
