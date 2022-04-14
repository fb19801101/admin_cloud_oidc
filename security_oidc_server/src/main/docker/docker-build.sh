#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-server
NAME=oidc-server

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12190:12190 \
           -v /crcc/oidc-project/log/oidc-server:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-server:/opt/oidc-project/config/oidc-server \
           --link eurka-server --name $NAME -d $IMG
