#!/bin/bash
cd /home/kavia/workspace/code-generation/moodtrackr-14647-cae79027/moodtrackr
./gradlew lint
LINT_EXIT_CODE=$?
if [ $LINT_EXIT_CODE -ne 0 ]; then
   exit 1
fi

