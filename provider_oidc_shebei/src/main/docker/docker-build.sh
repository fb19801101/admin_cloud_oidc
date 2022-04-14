#!/bin/bash
#chmod +x docker-build.sh

IMG=crcc/oidc-shebei
NAME=oidc-shebei

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 1g --restart=always -p 12194:12194 \
           -v /crcc/oidc-project/log/oidc-shebei:/crcc/tmp/log \
           -v /opt/oidc-project/config/oidc-shebei:/opt/oidc-project/config/oidc-shebei \
           --link eurka-server --name $NAME -d $IMG
