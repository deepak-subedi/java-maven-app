#!/usr/bin/env bash

docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi $(docker images -q)

docker compose -f docker-compose.yaml up --detach

