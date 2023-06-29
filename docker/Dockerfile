FROM amazoncorretto:17.0.7-alpine

# Default to UTF-8 file.encoding
ENV LANG C.UTF-8

COPY target/cards-1.0.jar app.jar

ENV JAVA_OPTS="-Djava.security.egd=file:/dev/./urandom"

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.12.0/wait /wait
RUN chmod +x /wait

CMD /wait && java $JAVA_OPTS -jar /app.jar