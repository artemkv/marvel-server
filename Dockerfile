FROM openjdk:8-jre

RUN mkdir -p /usr/local/bin/marvel
RUN mkdir -p /usr/local/bin/marvel/logs
RUN mkdir -p /usr/local/bin/marvel/data

WORKDIR /usr/local/bin/marvel

ARG JAR_FILE

COPY target/${JAR_FILE} /usr/local/bin/marvel/marvel-service.jar

EXPOSE 8500

ENV SERVER_ADDRESS=0.0.0.0
ENV SERVER_PORT=8500
ENV DB_CONNECTION_STRING=jdbc:h2:file:/usr/local/bin/marvel/data/marveldb;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
ENV LOGGING_PATH=/usr/local/bin/marvel/logs
ENV JAVA_OPTIONS -Xmx256m -Xms256m

CMD java $JAVA_OPTIONS -jar /usr/local/bin/marvel/marvel-service.jar