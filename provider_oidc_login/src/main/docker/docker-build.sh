#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-login
NAME=oidc-login

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12109:12109 \
           -v /crcc/oidc-project/log/oidc-login:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-login:/opt/oidc-project/config/oidc-login \
           --link eurka-server --name $NAME -d $IMG
