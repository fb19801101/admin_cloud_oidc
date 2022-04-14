#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-hunningtu
NAME=oidc-hunningtu

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12183:12183 \
           -v /crcc/oidc-project/log/oidc-hunningtu:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-hunningtu:/opt/oidc-project/config/oidc-hunningtu \
           --link eurka-server --name $NAME -d $IMG
