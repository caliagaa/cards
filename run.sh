#bin/bash

mvn package
cd docker
docker-compose up
