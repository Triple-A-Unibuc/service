#!/bin/bash
set -x

docker compose --profile postgres --profile hello-service up -d
