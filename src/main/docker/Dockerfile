FROM openjdk:8

MAINTAINER Chris Maki <chris.maki@ripcitysoftware.com>

ENV JAVA_APP_JAR=@project.build.finalName@.jar

ADD $JAVA_APP_JAR /
ADD run.sh /

RUN chmod 755 run.sh

VOLUME [ "/opt/rcs/" ]

HEALTHCHECK --interval=5m --timeout=3s \
  CMD curl -f http://localhost:8080/health

# run the script to start the app
CMD ["/run.sh"]