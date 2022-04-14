FROM java:8
MAINTAINER cr121.com

ADD gateway-0.0.1-SNAPSHOT.jar /crcc/

WORKDIR /crcc

EXPOSE 9999

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","gateway-0.0.1-SNAPSHOT.jar"]
