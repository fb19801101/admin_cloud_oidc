FROM java:8
MAINTAINER cr121.com

ADD register-0.0.1-SNAPSHOT.jar /crcc/

WORKDIR /crcc

EXPOSE 8888

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","register-0.0.1-SNAPSHOT.jar"]
