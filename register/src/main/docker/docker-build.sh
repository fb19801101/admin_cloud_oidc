#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/eurka-server
NAME=eurka-server

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 8888:8888 \
           -v /crcc/oidc-project/log/eurka-server:/crcc/tmp/log \
           --name $NAME -d $IMG
