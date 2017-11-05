#!/usr/bin/env bash

# See this page for why we are using these JVM options: https://hub.docker.com/_/openjdk/
JAVA_OPTIONS=" \
    -Dspring.profiles.active=prod
    -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap"

exec java $JAVA_OPTIONS -jar $JAVA_APP_JAR
