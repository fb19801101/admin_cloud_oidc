#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/gateway
NAME=gateway

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 9999:9999 \
           -v /crcc/oidc-project/log/gateway:/crcc/tmp/log \
           --name $NAME -d $IMG
