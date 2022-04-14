#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongchengbm
NAME=oidc-gongchengbm

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12197:12197 \
           -v /crcc/oidc-project/log/oidc-gongchengbm:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongchengbm:/opt/oidc-project/config/oidc-gongchengbm \
           --link eurka-server --name $NAME -d $IMG
