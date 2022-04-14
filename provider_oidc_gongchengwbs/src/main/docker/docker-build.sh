#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-gongchengwbs
NAME=oidc-gongchengwbs

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12191:12191 \
           -v /crcc/oidc-project/log/oidc-gongchengwbs:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-gongchengwbs:/opt/oidc-project/config/oidc-gongchengwbs \
           --link eurka-server --name $NAME -d $IMG
