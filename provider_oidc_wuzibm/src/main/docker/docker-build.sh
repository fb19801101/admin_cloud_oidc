#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-wuzibm
NAME=oidc-wuzibm

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12175:12175 \
           -v /crcc/oidc-project/log/oidc-wuzibm:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-wuzibm:/opt/oidc-project/config/oidc-wuzibm \
           --link eurka-server --name $NAME -d $IMG
