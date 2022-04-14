#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-laowufbs
NAME=oidc-laowufbs

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12172:12172 \
           -v /crcc/oidc-project/log/oidc-laowufbs:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-laowufbs:/opt/oidc-project/config/oidc-laowufbs \
           --link eurka-server --name $NAME -d $IMG
