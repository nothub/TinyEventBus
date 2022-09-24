FLAGS = --console plain --full-stacktrace

.PHONY: verify
verify:
	./gradlew $(FLAGS) check

.PHONY: publish
publish:
	./gradlew $(FLAGS) publishToSonatype closeAndReleaseStagingRepository
