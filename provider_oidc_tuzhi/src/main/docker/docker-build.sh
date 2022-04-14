#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-tuzhi
NAME=oidc-tuzhi

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12184:12184 \
           -v /crcc/oidc-project/log/oidc-tuzhi:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-tuzhi:/opt/oidc-project/config/oidc-tuzhi \
           --link eurka-server --name $NAME -d $IMG
