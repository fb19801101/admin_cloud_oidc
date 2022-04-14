FROM java:8
MAINTAINER dx185.com

ADD erp-timenetwork-impl-1.0.jar /dx/

WORKDIR /dx

EXPOSE 9025

ENTRYPOINT ["java","-Dspring.profiles.active=prod_test","-Djava.security.egd=file:/dev/./urandom","-Duser.timezone=GMT+8","-jar","erp-timenetwork-impl-1.0.jar"]
