#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gangjinchang
NAME=oidc-gangjinchang

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12182:12182 \
           -v /crcc/oidc-project/log/oidc-gangjinchang:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gangjinchang:/opt/oidc-project/config/oidc-gangjinchang \
           --link eurka-server --name $NAME -d $IMG
