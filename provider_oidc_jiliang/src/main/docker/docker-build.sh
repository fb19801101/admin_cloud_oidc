#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-jiliang
NAME=oidc-jiliang

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12181:12181 \
           -v /crcc/oidc-project/log/oidc-jiliang:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-jiliang:/opt/oidc-project/config/oidc-jiliang \
           --link eurka-server --name $NAME -d $IMG
