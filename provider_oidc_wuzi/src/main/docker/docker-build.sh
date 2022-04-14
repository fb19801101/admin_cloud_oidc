#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-wuzi
NAME=oidc-wuzi

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12195:12195 \
           -v /crcc/oidc-project/log/oidc-wuzi:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-wuzi:/opt/oidc-project/config/oidc-wuzi \
           --link eurka-server --name $NAME -d $IMG
