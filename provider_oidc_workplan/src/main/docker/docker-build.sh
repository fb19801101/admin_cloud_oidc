#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-workplan
NAME=oidc-workplan

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12187:12187 \
           -v /crcc/oidc-project/log/oidc-workplan:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-workplan:/opt/oidc-project/config/oidc-workplan \
           --link eurka-server --name $NAME -d $IMG
