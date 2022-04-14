#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-pingjiafbs
NAME=oidc-pingjiafbs

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12171:12171 \
           -v /crcc/oidc-project/log/oidc-pingjiafbs:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-pingjiafbs:/opt/oidc-project/config/oidc-pingjiafbs \
           --link eurka-server --name $NAME -d $IMG
