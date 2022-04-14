#!/bin/bash

IMG=dxtest/test-erp-timenetwork
NAME=test-erp-timenetwork

docker stop $NAME
docker rm $(docker ps -qf status=exited)
docker rmi $IMG
docker build -t $IMG .
docker run -m 4g --restart=always -p 19025:9025 -v /dxpro/dxtest/log/timenetwork:/dx/tmp/log -v /mnt/FileDev_test:/dx/uploadFile  --link zookeeper_3.4.14_test --link redis_5.0_test --link mysql_5.6 --name $NAME -d $IMG
