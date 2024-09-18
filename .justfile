_targets:
  @just --list --unsorted --list-heading $'Available targets:\n' --list-prefix "  "

# update the top-level flake lock file
@update-flake:
  nix flake update --commit-lock-file --commit-lockfile-summary "update Nix flake inputs"

# upgrade dependencies across all Gradle projects
@versions:
  ./gradlew --no-configuration-cache refreshVersionsMigrate --mode=VersionsPropertiesOnly && ./gradlew refreshVersions

# run tests across all projects
@check:
  ./gradlew check
