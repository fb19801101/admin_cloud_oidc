#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongchengsl
NAME=oidc-gongchengsl

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12185:12185 \
           -v /crcc/oidc-project/log/oidc-gongchengsl:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongchengsl:/opt/oidc-project/config/oidc-gongchengsl \
           --link eurka-server --name $NAME -d $IMG
