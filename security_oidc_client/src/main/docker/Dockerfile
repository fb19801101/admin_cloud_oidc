FROM java:8
MAINTAINER cr121.com

ADD security_oidc_client-0.0.1-SNAPSHOT.jar /crcc/

WORKDIR /crcc

EXPOSE 12199

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","security_oidc_client-0.0.1-SNAPSHOT.jar"]
