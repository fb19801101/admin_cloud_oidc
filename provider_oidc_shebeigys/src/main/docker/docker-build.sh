#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-shebeigys
NAME=oidc-shebeigys

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12192:12192 \
           -v /crcc/oidc-project/log/oidc-shebeigys:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-shebeigys:/opt/oidc-project/config/oidc-shebeigys \
           --link eurka-server --name $NAME -d $IMG
