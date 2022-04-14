#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-dynamicplan
NAME=oidc-dynamicplan

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12188:12188 \
           -v /crcc/oidc-project/log/oidc-dynamicplan:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-dynamicplan:/opt/oidc-project/config/oidc-dynamicplan \
           --link eurka-server --name $NAME -d $IMG
