#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-yangong
NAME=oidc-yangong

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12178:12178 \
           -v /crcc/oidc-project/log/oidc-yangong:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-yangong:/opt/oidc-project/config/oidc-yangong \
           --link eurka-server --name $NAME -d $IMG
