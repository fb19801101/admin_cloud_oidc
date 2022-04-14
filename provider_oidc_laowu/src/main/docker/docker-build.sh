#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-laowu
NAME=oidc-laowu

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12196:12196 \
           -v /crcc/oidc-project/log/oidc-laowu:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-laowu:/opt/oidc-project/config/oidc-laowu \
           --link eurka-server --name $NAME -d $IMG
