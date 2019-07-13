FROM openjdk:12-alpine as builder

COPY . /lib
WORKDIR /lib

RUN apk add --update maven
RUN mvn package

FROM openjdk:12-alpine
COPY --from=builder /lib/target/libr-1.0.0-SNAPSHOT-exec.jar /opt/lib/lib.jar

CMD ["java", "-jar", "/opt/lib/lib.jar"]