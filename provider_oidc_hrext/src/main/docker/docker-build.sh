#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-hrext
NAME=oidc-hrext

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12173:12173 \
           -v /crcc/oidc-project/log/oidc-hrext:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-hrext:/opt/oidc-project/config/oidc-hrext \
           --link eurka-server --name $NAME -d $IMG
