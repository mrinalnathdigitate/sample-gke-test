FROM eclipse-temurin:11.0.16_8-jdk-alpine


#ENV http_proxy "http://10.150.5.6:8080"
#ENV https_proxy "http://10.150.5.6:8080"

RUN apk --update --no-cache add tzdata ttf-dejavu curl busybox-extras

ENV http_proxy ""
ENV https_proxy ""

#RUN addgroup -S -g 3000 ignio && adduser -S -D -G ignio -h /opt/ -u 1000 ignio

RUN mkdir -p /opt/ /opt/application
RUN  /opt/

COPY entrypoint.sh /opt/application/entrypoint.sh
RUN chmod +x /opt/application/entrypoint.sh

COPY target/sample.jar /opt//application/sample.jar

ENV JAVA_MEMORY_OPTS="-Xms1g -Xmx8g"
ENV JAVA_OPTS="-XX:+UseTLAB -XX:NewSize=128m -XX:MaxNewSize=128m -XX:MaxTenuringThreshold=0 -XX:SurvivorRatio=1024 -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=40 -XX:MaxGCPauseMillis=1000 -XX:InitiatingHeapOccupancyPercent=50 -XX:+UseCompressedOops -XX:ParallelGCThreads=8 -XX:ConcGCThreads=8 -XX:+DisableExplicitGC -Djavax.security.auth.useSubjectCredsOnly=false"
ENV JAVA_EXTRA_OPTS=""

WORKDIR /opt//application
ENTRYPOINT ["./entrypoint.sh"]