#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongchengkb
NAME=oidc-gongchengkb

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12177:12177 \
           -v /crcc/oidc-project/log/oidc-gongchengkb:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongchengkb:/opt/oidc-project/config/oidc-gongchengkb \
           --link eurka-server --name $NAME -d $IMG
