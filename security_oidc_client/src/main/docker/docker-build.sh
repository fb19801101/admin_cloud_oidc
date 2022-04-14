#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-client
NAME=oidc-client

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 4g --restart=always -p 12199:12199 \
           -v /crcc/oidc-project/log/oidc-client:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-client:/opt/oidc-project/config/oidc-client \
           --link eurka-server --name $NAME -d $IMG
