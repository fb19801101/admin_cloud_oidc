#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongcheng
NAME=oidc-gongcheng

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12198:12198 \
           -v /crcc/oidc-project/log/oidc-gongcheng:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongcheng:/opt/oidc-project/config/oidc-gongcheng \
           --link eurka-server --name $NAME -d $IMG
