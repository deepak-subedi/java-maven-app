#!/usr/bin/env bash

export IMAGE_NAME=$1
export IMAGE_VERSION=$2
export DOCKER_USER=$3
export DOCKER_PWD=$4

# docker compose -f docker-compose.yaml down
# docker rmi $(docker images -q)

echo $DOCKER_PWD | docker login -u $DOCKER_USER --password-stdin
docker compose -f docker-compose.yaml up --detach