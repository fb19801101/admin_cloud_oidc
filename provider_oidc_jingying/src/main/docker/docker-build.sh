#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-jingying
NAME=oidc-jingying

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12176:12176 \
           -v /crcc/oidc-project/log/oidc-jingying:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-jingying:/opt/oidc-project/config/oidc-jingying \
           --link eurka-server --name $NAME -d $IMG
