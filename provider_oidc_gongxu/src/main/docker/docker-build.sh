#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongxu
NAME=oidc-gongxu

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12186:12186 \
           -v /crcc/oidc-project/log/oidc-gongxu:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongxu:/opt/oidc-project/config/oidc-gongxu \
           --link eurka-server --name $NAME -d $IMG
