FROM openjdk:21-jdk-slim
LABEL author="tauber-caglianone"

RUN apt update && apt install -y netcat-openbsd

WORKDIR /app

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar
COPY scripts/wait-for-it.sh /usr/local/bin/wait-for-it.sh

RUN chmod +x /usr/local/bin/wait-for-it.sh

ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "tcm-rabbitmq", "5672", "--", "/usr/local/bin/wait-for-it.sh", "tcm-eureka-server-service", "8761", "--", "java", "-jar", "app.jar"]
