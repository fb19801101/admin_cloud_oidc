#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-shebeibm
NAME=oidc-shebeibm

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12174:12174 \
           -v /crcc/oidc-project/log/oidc-shebeibm:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-shebeibm:/opt/oidc-project/config/oidc-shebeibm \
           --link eurka-server --name $NAME -d $IMG
