#!/usr/bin/env bash

docker compose -f docker-compose.yaml down
docker rmi $(docker images -q)

docker compose -f docker-compose.yaml up --detach

