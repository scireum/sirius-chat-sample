# docker build -t sirius-chat .
FROM openjdk:8
COPY ./target/ /tmp
COPY ./start.sh /tmp/start.sh
RUN chmod a+x /tmp/start.sh
WORKDIR /tmp
EXPOSE 80
CMD ./start.sh