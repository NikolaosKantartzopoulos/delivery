
# 🛠️ Commit: Add Commit Message Check to Build Workflow

This change introduces a reusable workflow to validate commit message format and integrates it into the main build pipeline.

---

## ✅ What Changed

### 1. `.github/workflows/check-commit-message.yml` (New Reusable Workflow)

A new reusable GitHub Actions workflow was added to enforce a consistent commit message format.

#### Trigger
- This workflow is triggered **only** by `workflow_call` to allow it to be reused from other workflows.

#### Commit Message Rule
Commit messages must match the following pattern:

```regex
^(Github|Docker|Gradle|Spring|Pitstop|Fix|Tests):\s.*
```

#### Example Valid Messages
- `Gradle: Update build plugins`
- `Fix: Address null pointer issue`
- `Docker: Modify container startup logic`

#### Workflow Content:
```yaml
name: Check Commit Message

on:
  workflow_call:

jobs:
  check-commit-message:
    runs-on: ubuntu-latest
    steps:
      - name: Check Commit Type
        uses: gsactions/commit-message-checker@v2
        with:
          pattern: '^(Github|Docker|Gradle|Spring|Pitstop|Fix|Tests):\s.*'
          error: "❌ Invalid format: commit message must start with one of [Github, Docker, Gradle, Spring, Pitstop, Fix, Tests], followed by a colon and a space (e.g., Gradle: Update build logic)."
```

---

### 2. `.github/workflows/check-build.yml` (Updated)

The existing build workflow was updated to **call the commit message check before running the build**.

#### Key Addition:
```yaml
jobs:
  check-commit-message:
    uses: ./.github/workflows/check-commit-message.yml

  build:
    needs: check-commit-message
```

This ensures the build job only runs if the commit message passes validation.

---

## 🔒 Benefit

By enforcing structured commit messages, this change improves readability, changelog automation potential, and contributor discipline across the project.
