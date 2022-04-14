FROM java:8
MAINTAINER cr121.com

ADD provider_oidc_workplan-0.0.1-SNAPSHOT.jar /crcc/

WORKDIR /crcc

EXPOSE 12187

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","provider_oidc_workplan-0.0.1-SNAPSHOT.jar"]
