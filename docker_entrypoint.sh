#!/usr/bin/env bash

set -e

./mvnw clean spring-boot:run -Dspring-boot.run.profiles=dev

while true; do
  echo "Building"
  watch -d -t -g "ls -lR ./src | md5sum" && ./mvnw compile
  echo "Change detected"
done
aa
