#!/bin/bash

java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=4000  -jar target/angular-phantom-renderer-1.0-SNAPSHOT.jar server src/main/resources/origin-config.yml
