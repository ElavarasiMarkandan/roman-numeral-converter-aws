#!/bin/bash

cd ..
mvn clean package -Dmaven.test.skip

cd docker
docker-compose -f docker-compose.yml up  -d --build
docker-compose -f docker-compose.yml ps
