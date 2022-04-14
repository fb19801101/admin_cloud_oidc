#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-wuzigys
NAME=oidc-wuzigys

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12193:12193 \
           -v /crcc/oidc-project/log/oidc-wuzigys:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-wuzigys:/opt/oidc-project/config/oidc-wuzigys \
           --link eurka-server --name $NAME -d $IMG
