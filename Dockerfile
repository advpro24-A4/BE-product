FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /src/advprog
COPY . .

ARG DATABASE_URL=${DATABASE_URL}
ARG DATABASE_USERNAME=${DATABASE_USERNAME}
ARG DATABASE_PASSWORD=${DATABASE_PASSWORD}
ARG KAFKA_BOOTSTRAP_SERVERS=${KAFKA_BOOTSTRAP_SERVERS}
ARG KAFKA_USERNAME=${KAFKA_USERNAME}
ARG KAFKA_PASSWORD=${KAFKA_PASSWORD}
ARG KAFKA_CONSUMER_GROUP_ID=${KAFKA_CONSUMER_GROUP_ID}

RUN chmod +x ./gradlew
RUN ./gradlew clean bootJar

FROM eclipse-temurin:21-jre-alpine AS runner

ARG USER_NAME=advprog
ARG USER_UID=1000
ARG USER_GID=${USER_UID}

RUN addgroup -g ${USER_GID} ${USER_NAME} && adduser -h /opt/youkosoproduct -D -u ${USER_UID} -G ${USER_NAME} ${USER_NAME}

USER ${USER_NAME}
WORKDIR /opt/youkosoproduct
COPY --from=builder --chown=${USER_UID}:%{USER_GID} /src/advprog/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]